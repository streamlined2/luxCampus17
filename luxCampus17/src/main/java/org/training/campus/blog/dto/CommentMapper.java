package org.training.campus.blog.dto;

import org.springframework.stereotype.Component;
import org.training.campus.blog.model.Comment;

@Component
public class CommentMapper {

	public CommentDto toDto(Comment comment) {
		return new CommentDto(comment.getId(), comment.getText(), comment.getCreationDate());
	}

	public Comment toComment(CommentDto dto) {
		return Comment.builder().id(dto.id()).text(dto.text()).creationDate(dto.creationDate()).build();
	}

}
