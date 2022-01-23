package org.training.campus.blog.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.training.campus.blog.model.Comment;
import org.training.campus.blog.model.Post;

@Component
public class PostCommentMapper {
	
	private final CommentMapper commentMapper;
	
	@Autowired
	public PostCommentMapper(CommentMapper commentMapper){
		this.commentMapper = commentMapper;
	}
	
	public PostCommentDto toDto(Post post, Comment[] comments) {
		List<CommentDto> commentDTOs = new ArrayList<>();
		for(var comment:comments) {
			commentDTOs.add(commentMapper.toDto(comment));
		}
		return toDto(post, commentDTOs.toArray(new CommentDto[0]));
	}
	
	public PostCommentDto toDto(Post post, CommentDto[] comments) {
		return new PostCommentDto(post.getId(), post.getTitle(), post.getContent(), post.isStar(), comments);
	}
	
	public Post toPost(PostCommentDto dto) {
		return Post.builder().id(dto.id()).title(dto.title()).content(dto.content()).star(dto.star()).build();
	}
	
}
