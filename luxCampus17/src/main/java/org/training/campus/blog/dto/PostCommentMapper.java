package org.training.campus.blog.dto;

import org.springframework.stereotype.Component;
import org.training.campus.blog.model.Comment;
import org.training.campus.blog.model.Post;

@Component
public class PostCommentMapper {
	
	public PostCommentDTO toDto(Post post, Comment[] comments) {
		return new PostCommentDTO(post.getId(), post.getTitle(), post.getContent(), post.isStar(), comments);
	}
	
	public Post toPost(PostCommentDTO dto) {
		return Post.builder().id(dto.id()).title(dto.title()).content(dto.content()).star(dto.star()).build();
	}
	
}
