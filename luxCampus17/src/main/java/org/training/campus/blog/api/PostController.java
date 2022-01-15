package org.training.campus.blog.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.training.campus.blog.model.Post;
import org.training.campus.blog.service.PostService;

@RestController
@RequestMapping("api/v1/posts")
public class PostController {

	@Autowired
	private PostService postService;
	
	@GetMapping
	public List<Post> getAll(){
		return postService.findAll();
	}
	
	@GetMapping("/{id}")
	public Post getById(@PathVariable("id") Long id){
		return postService.findById(id).orElse(null);
	}
	
	@PostMapping
	public void add(@RequestBody Post post) {
		postService.save(post);
	}
	
	@PutMapping("/{id}")
	public void modify(@PathVariable("id") Long id, @RequestBody Post post) {
		post.setId(id);
		postService.save(post);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		postService.deleteById(id);
	}

}
