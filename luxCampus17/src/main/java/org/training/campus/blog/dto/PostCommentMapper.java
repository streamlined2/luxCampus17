package org.training.campus.blog.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.training.campus.blog.model.Comment;
import org.training.campus.blog.model.Post;

@Component
public class PostCommentMapper {
	
	@Autowired
	private CommentMapper commentMapper;
	
	public PostCommentMapper(CommentMapper commentMapper){
		this.commentMapper = commentMapper;
	}
	
	public PostCommentDTO toDto(Post post, Comment[] comments) {
		List<CommentDTO> commentDTOs = new ArrayList<>();
		for(var comment:comments) {
			commentDTOs.add(commentMapper.toDto(comment));
		}
		return new PostCommentDTO(post.getId(), post.getTitle(), post.getContent(), post.isStar(), commentDTOs.toArray(new CommentDTO[0]));
	}
	
	public Post toPost(PostCommentDTO dto) {
		return Post.builder().id(dto.id()).title(dto.title()).content(dto.content()).star(dto.star()).build();
	}
	
}
