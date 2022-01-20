package org.training.campus.blog.dto;

import org.springframework.stereotype.Component;
import org.training.campus.blog.model.Post;

@Component
public class PostMapper {
	
	public PostDTO toDto(Post post) {
		return new PostDTO(post.getId(), post.getTitle(), post.getContent(), post.isStar());
	}
	
	public Post toPost(PostDTO dto) {
		return Post.builder().id(dto.id()).title(dto.title()).content(dto.content()).star(dto.star()).build();
	}

}
