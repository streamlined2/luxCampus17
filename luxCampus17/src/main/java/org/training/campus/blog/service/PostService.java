package org.training.campus.blog.service;

import java.util.List;
import java.util.Optional;

import org.training.campus.blog.dto.PostCommentDto;
import org.training.campus.blog.dto.PostDto;

public interface PostService {

	List<PostDto> findAll();

	List<PostDto> findAll(Optional<String> title, Optional<String> sortProperty);

	PostDto save(PostDto postDto);

	PostDto save(Long id, PostDto postDto);

	Optional<PostDto> findById(Long id);
	
	void deleteById(Long id);
	
	List<PostDto> findAllStarred();
	
	boolean placeMark(Long id, boolean value);

	Optional<PostCommentDto> getPostComments(Long postId);

}
