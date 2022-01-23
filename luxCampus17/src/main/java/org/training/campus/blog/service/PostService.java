package org.training.campus.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.training.campus.blog.dao.PostDao;
import org.training.campus.blog.dto.CommentDto;
import org.training.campus.blog.dto.PostCommentDTO;
import org.training.campus.blog.dto.PostCommentMapper;
import org.training.campus.blog.dto.PostDto;
import org.training.campus.blog.dto.PostMapper;
import org.training.campus.blog.model.Comment;
import org.training.campus.blog.model.Post;
import org.training.campus.blog.model.Post.PostBuilder;

@Service
public class PostService {

	private final PostDao dao;
	private final PostMapper postMapper;
	private final PostCommentMapper postCommentMapper;
	private final CommentService commentService;

	@Autowired
	private PostService(PostDao dao, PostMapper postMapper, PostCommentMapper postCommentMapper,
			CommentService commentService) {
		this.dao = dao;
		this.postMapper = postMapper;
		this.postCommentMapper = postCommentMapper;
		this.commentService = commentService;
	}

	public List<PostDto> findAll() {
		return findAll(Optional.empty(), Optional.empty());
	}

	public List<PostDto> findAll(Optional<String> title, Optional<String> sortProperty) {
		PostBuilder builder = Post.builder();
		title.ifPresent(builder::title);
		var matcher = ExampleMatcher.matchingAll().withMatcher("title", GenericPropertyMatchers.contains().ignoreCase())
				.withIgnorePaths("id", "star");
		List<Post> postList = dao.findAll(Example.of(builder.build(), matcher),
				sortProperty.map(Sort::by).orElse(Sort.unsorted()));
		return postList.stream().map(postMapper::toDto).toList();
	}

	public PostDto save(PostDto postDto) {
		return postMapper.toDto(dao.save(postMapper.toPost(postDto)));
	}

	public PostDto save(Long id, PostDto postDto) {
		Post post = postMapper.toPost(postDto);
		post.setId(id);
		return postMapper.toDto(dao.save(post));
	}

	public Optional<PostDto> findById(Long id) {
		return dao.findById(id).map(postMapper::toDto);
	}

	public void deleteById(Long id) {
		dao.deleteById(id);
	}

	public List<PostDto> findAllStarred() {
		Post starredPost = Post.builder().star(true).build();
		var matcher = ExampleMatcher.matchingAll().withMatcher("star", GenericPropertyMatchers.exact())
				.withIgnorePaths("id");
		List<Post> postList = dao.findAll(Example.of(starredPost, matcher));
		return postList.stream().map(postMapper::toDto).toList();
	}

	public boolean placeMark(Long id, boolean value) {
		Optional<Post> postData = dao.findById(id);
		if (postData.isPresent()) {
			Post post = postData.get();
			post.setStar(value);
			dao.save(post);
			return true;
		}
		return false;
	}

	public Optional<PostCommentDTO> getPostComments(Long postId) {
		Optional<Post> postData = dao.findById(postId);
		if (postData.isPresent()) {
			Post post = postData.get();
			List<CommentDto> commentDtoList = commentService.getCommentsForPost(post.getId());
			return Optional.of(postCommentMapper.toDto(post, commentDtoList.toArray(new CommentDto[0])));
		}
		return Optional.empty();
	}

}
