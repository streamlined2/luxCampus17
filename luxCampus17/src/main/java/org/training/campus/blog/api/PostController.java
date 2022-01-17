package org.training.campus.blog.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.training.campus.blog.model.Post;
import org.training.campus.blog.service.PostService;

@RestController
@RequestMapping("api/v1/posts")
public class PostController {

	@Autowired
	private PostService postService;

	@GetMapping
	public List<Post> getAll(@RequestParam(name = "title", required = false) String title,
			@RequestParam(name = "sort", required = false) String sort) {
		Map<String, String> filterMap = new HashMap<>();
		if (title != null) {
			filterMap.put("title", title);
		}
		List<String> sortList = new ArrayList<>();
		if (sort != null) {
			sortList.add(sort);
		}
		return postService.findAll();//filterMap, sortList
	}

	@GetMapping("/{id}")
	public Post getById(@PathVariable("id") Long id) {
		return postService.findById(id).orElse(null);
	}

	@PostMapping
	public Long add(@RequestBody Post post) {
		return postService.save(post).getId();
	}

	@PutMapping("/{id}")
	public void modify(@PathVariable("id") Long id, @RequestBody Post post) {
		postService.save(id, post);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		postService.deleteById(id);
	}

}
