package org.training.campus.blog.dto;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.StringJoiner;

import org.training.campus.blog.model.Comment;

public record PostCommentDTO(long id, String title, String content, boolean star, Comment[] comments) {

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof PostCommentDTO dto) {
			return id==dto.id;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		StringJoiner join = new StringJoiner(",","[","]");
		join.add(String.valueOf(id)).add(title).add(content).add(String.valueOf(star));
		for(Comment comment:comments) {
			StringJoiner commentJoin = new StringJoiner(",","(",")");
			commentJoin.add(String.valueOf(id)).add(comment.getText()).add(DateTimeFormatter.ISO_DATE.format(comment.getCreationDate()));
			join.add(commentJoin.toString());			
		}
		return join.toString();
	}

}
