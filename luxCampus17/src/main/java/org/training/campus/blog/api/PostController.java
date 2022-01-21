package org.training.campus.blog.api;

import java.util.List;
import java.util.Optional;

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
import org.training.campus.blog.dto.PostCommentDTO;
import org.training.campus.blog.dto.PostDTO;
import org.training.campus.blog.dto.PostMapper;
import org.training.campus.blog.service.PostService;

@RestController
@RequestMapping("api/v1/posts")
public class PostController {

	private final PostService postService;
	private final PostMapper postMapper;

	@Autowired
	private PostController(PostService postService, PostMapper postMapper) {
		this.postService = postService;
		this.postMapper = postMapper;
	}

	@GetMapping
	public List<PostDTO> getAll(@RequestParam(name = "title", required = false) String title,
			@RequestParam(name = "sort", required = false) String sort) {
		return postService.findAll(Optional.ofNullable(title), Optional.ofNullable(sort)).stream()
				.map(postMapper::toDto).toList();
	}

	@GetMapping("/{id}")
	public PostDTO getById(@PathVariable("id") Long id) {
		return postService.findById(id).map(postMapper::toDto).orElse(null);
	}

	@PostMapping
	public Long add(@RequestBody PostDTO postDto) {
		return postService.save(postMapper.toPost(postDto)).getId();
	}

	@PutMapping("/{id}")
	public void modify(@PathVariable("id") Long id, @RequestBody PostDTO postDto) {
		postService.save(id, postMapper.toPost(postDto));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		postService.deleteById(id);
	}

	@GetMapping("/star")
	public List<PostDTO> getAllStarred() {
		return postService.findAllStarred().stream().map(postMapper::toDto).toList();
	}

	@PutMapping("/{id}/star")
	public boolean markAsStarred(@PathVariable("id") Long id) {
		return postService.placeMark(id, true);
	}

	@DeleteMapping("/{id}/star")
	public boolean removeStarredMark(@PathVariable("id") Long id) {
		return postService.placeMark(id, false);
	}
	
	@GetMapping("/{postId}/full")
	public PostCommentDTO postCommentFullListing(@PathVariable("postId") Long postId) {
		return null;
	}

}
