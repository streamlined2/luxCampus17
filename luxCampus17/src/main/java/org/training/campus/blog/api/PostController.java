package org.training.campus.blog.api;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.training.campus.blog.dto.PostCommentDto;
import org.training.campus.blog.dto.PostDto;
import org.training.campus.blog.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/posts")
public class PostController {

	private final PostService postService;

	@GetMapping
	public List<PostDto> getAll(@RequestParam Optional<String> title, @RequestParam Optional<String> sort) {
		return postService.findAll(title, sort);
	}

	@GetMapping("/tag")
	public List<PostDto> getPostsByTags(@RequestParam Set<Long> tags) {
		return postService.findPostsByTags(tags);
	}

	@GetMapping("/{id}")
	public PostDto getById(@PathVariable Long id) {
		return postService.findById(id).orElse(null);
	}

	@PostMapping
	public Long add(@RequestBody PostDto postDto) {
		return postService.save(postDto).id();
	}

	@PutMapping("/{id}")
	public void modify(@PathVariable Long id, @RequestBody PostDto postDto) {
		postService.save(id, postDto);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		postService.deleteById(id);
	}

	@PutMapping("/{postId}/tag/{tagId}")
	public boolean markWithTag(@PathVariable Long postId, @PathVariable Long tagId) {
		return postService.markWithTag(postId, tagId);
	}

	@DeleteMapping("/{postId}/tag/{tagId}")
	public boolean removeTag(@PathVariable Long postId, @PathVariable Long tagId) {
		return postService.removeTag(postId, tagId);
	}

	@GetMapping("/star")
	public List<PostDto> getAllStarred() {
		return postService.findAllStarred();
	}

	@PutMapping("/{id}/star")
	public boolean markAsStarred(@PathVariable Long id) {
		return postService.placeMark(id, true);
	}

	@DeleteMapping("/{id}/star")
	public boolean removeStarredMark(@PathVariable Long id) {
		return postService.placeMark(id, false);
	}

	@GetMapping("/{postId}/full")
	public PostCommentDto postCommentFullListing(@PathVariable Long postId) {
		return postService.getPostComments(postId).orElse(null);
	}

}
