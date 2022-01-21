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
import org.training.campus.blog.dto.CommentDTO;
import org.training.campus.blog.dto.CommentMapper;
import org.training.campus.blog.model.Comment;
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
	public CommentDTO getCommentForPost(@PathVariable("postId") Long postId,
			@PathVariable("commentId") Long commentId) {
		return commentService.getCommentForPost(postId, commentId).map(commentMapper::toDto).orElse(null);
	}

	@PostMapping("/{postId}/comments")
	public Long add(@PathVariable("postId") Long postId, @RequestBody CommentDTO commentDto) {
		return commentService.add(postId, commentMapper.toComment(commentDto)).map(Comment::getId).orElse(null);
	}

	@PutMapping("/{postId}/comments/{commentId}")
	public boolean modify(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId,
			@RequestBody CommentDTO commentDto) {
		// TODO
		return false;
	}

}
