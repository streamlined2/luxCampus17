package org.training.campus.blog.dto;

import org.springframework.stereotype.Component;
import org.training.campus.blog.model.Post;

@Component
public class PostMapper {
	
	public PostDto toDto(Post post) {
		return new PostDto(post.getId(), post.getTitle(), post.getContent(), post.isStar());
	}
	
	public Post toPost(PostDto dto) {
		return Post.builder().id(dto.id()).title(dto.title()).content(dto.content()).star(dto.star()).build();
	}

}
