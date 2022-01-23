package org.training.campus.blog.dto;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.StringJoiner;

public record PostCommentDTO(long id, String title, String content, boolean star, CommentDto[] comments) {

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
		for(var comment:comments) {
			StringJoiner commentJoin = new StringJoiner(",","(",")");
			commentJoin.add(String.valueOf(id)).add(comment.text()).add(DateTimeFormatter.ISO_DATE.format(comment.creationDate()));
			join.add(commentJoin.toString());			
		}
		return join.toString();
	}

}
