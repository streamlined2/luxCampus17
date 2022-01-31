package org.training.campus.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.training.campus.blog.dao.PostDao;
import org.training.campus.blog.dto.CommentDto;
import org.training.campus.blog.dto.PostCommentDto;
import org.training.campus.blog.dto.PostCommentMapper;
import org.training.campus.blog.dto.PostDto;
import org.training.campus.blog.dto.PostMapper;
import org.training.campus.blog.model.Post;
import org.training.campus.blog.model.Post.PostBuilder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
public class DefaultPostService implements PostService {

	private final PostDao dao;
	private final PostMapper postMapper;
	private final PostCommentMapper postCommentMapper;
	private final DefaultCommentService commentService;

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

	@Transactional(readOnly = false)
	public PostDto save(PostDto postDto) {
		return postMapper.toDto(dao.save(postMapper.toPost(postDto)));
	}

	@Transactional(readOnly = false)
	public PostDto save(Long id, PostDto postDto) {
		Post post = postMapper.toPost(postDto);
		post.setId(id);
		return postMapper.toDto(dao.save(post));
	}

	public Optional<PostDto> findById(Long id) {
		return dao.findById(id).map(postMapper::toDto);
	}

	@Transactional(readOnly = false)
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

	@Transactional(readOnly = false)
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

	public Optional<PostCommentDto> getPostComments(Long postId) {
		Optional<Post> postData = dao.findById(postId);
		if (postData.isPresent()) {
			Post post = postData.get();
			List<CommentDto> commentDtoList = commentService.getCommentsForPost(post.getId());
			return Optional.of(postCommentMapper.toDto(post, commentDtoList.toArray(new CommentDto[0])));
		}
		return Optional.empty();
	}

}
