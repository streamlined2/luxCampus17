package org.training.campus.blog.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.training.campus.blog.dto.CommentDto;
import org.training.campus.blog.service.CommentService;

@RestController
@RequestMapping("api/v1/posts")
public class CommentController {

	private final CommentService commentService;

	@Autowired
	private CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@GetMapping("/{postId}/comments")
	public List<CommentDto> getCommentsForPost(@PathVariable("postId") Long postId) {
		return commentService.getCommentsForPost(postId);
	}

	@GetMapping("/{postId}/comments/{commentId}")
	public CommentDto getCommentForPost(@PathVariable("postId") Long postId,
			@PathVariable("commentId") Long commentId) {
		return commentService.getCommentForPost(postId, commentId).orElse(null);
	}

	@PostMapping("/{postId}/comments")
	public Long add(@PathVariable("postId") Long postId, @RequestBody CommentDto commentDto) {
		return commentService.add(postId, commentDto).map(CommentDto::id).orElse(null);
	}

	@PutMapping("/{postId}/comments/{commentId}")
	public void modify(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId,
			@RequestBody CommentDto commentDto) {
		commentService.save(commentId, commentDto);
	}

}
