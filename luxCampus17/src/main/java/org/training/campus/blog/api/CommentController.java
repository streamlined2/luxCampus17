package org.training.campus.blog.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.training.campus.blog.dto.CommentDTO;
import org.training.campus.blog.dto.CommentMapper;
import org.training.campus.blog.service.CommentService;

@RestController
@RequestMapping("api/v1/posts")
public class CommentController {

	private final CommentService commentService;
	private final CommentMapper commentMapper;

	@Autowired
	private CommentController(CommentService commentService, CommentMapper commentMapper) {
		this.commentService = commentService;
		this.commentMapper = commentMapper;
	}

	@GetMapping("/{postId}/comments")
	public List<CommentDTO> getCommentsForPost(@PathVariable("postId") Long postId) {
		return commentService.getCommentsForPost(postId).stream().map(commentMapper::toDto).toList();
	}

	@GetMapping("/{postId}/comments/{commentId}")
	public Optional<CommentDTO> getCommentForPost(@PathVariable("postId") Long postId,
			@PathVariable("commentId") Long commentId) {
		// TODO
		return Optional.empty();
	}

	@PostMapping("/{postId}/comments")
	public Long createComment(@PathVariable("postid") Long postId, @RequestBody CommentDTO comment) {
		// TODO
		return null;
	}

	@PutMapping("/{postId}/comments/{commentId}")
	public boolean modifyComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId,
			@RequestBody CommentDTO comment) {
		// TODO
		return false;
	}

}
