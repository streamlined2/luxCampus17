package org.training.campus.blog.service;

import java.util.List;
import java.util.Optional;

import org.training.campus.blog.dto.CommentDto;

public interface CommentService {

	List<CommentDto> getCommentsForPost(Long postId);

	Optional<CommentDto> getCommentForPost(Long commentId);

	Optional<CommentDto> add(Long postId, CommentDto commentDto);

	Optional<CommentDto> save(Long commentId, CommentDto commentDto);
	
	void delete(Long commentId);

}
