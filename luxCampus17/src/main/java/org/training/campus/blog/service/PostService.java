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
import org.training.campus.blog.dto.PostCommentDTO;
import org.training.campus.blog.dto.PostCommentMapper;
import org.training.campus.blog.model.Comment;
import org.training.campus.blog.model.Post;
import org.training.campus.blog.model.Post.PostBuilder;

@Service
public class PostService {

	private final PostDao dao;
	private final PostCommentMapper postCommentMapper;
	private final CommentService commentService;

	@Autowired
	private PostService(PostDao dao, PostCommentMapper postCommentMapper, CommentService commentService) {
		this.dao = dao;
		this.postCommentMapper = postCommentMapper;
		this.commentService = commentService;
	}

	public List<Post> findAll() {
		return findAll(Optional.empty(), Optional.empty());
	}

	public List<Post> findAll(Optional<String> title, Optional<String> sortProperty) {
		PostBuilder builder = Post.builder();
		title.ifPresent(builder::title);
		var matcher = ExampleMatcher.matchingAll().withMatcher("title", GenericPropertyMatchers.contains().ignoreCase())
				.withIgnorePaths("id", "star");
		return dao.findAll(Example.of(builder.build(), matcher), sortProperty.map(Sort::by).orElse(Sort.unsorted()));
	}

	public <T extends Post> T save(Post post) {
		return dao.save(post);
	}

	public <T extends Post> T save(Long id, Post post) {
		post.setId(id);
		return dao.save(post);
	}

	public Optional<Post> findById(Long id) {
		return dao.findById(id);
	}

	public void deleteById(Long id) {
		dao.deleteById(id);
	}

	public List<Post> findAllStarred() {
		Post starredPost = Post.builder().star(true).build();
		var matcher = ExampleMatcher.matchingAll().withMatcher("star", GenericPropertyMatchers.exact())
				.withIgnorePaths("id");
		return dao.findAll(Example.of(starredPost, matcher));
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
			List<Comment> comments = commentService.getCommentsForPost(post.getId());
			return Optional.of(postCommentMapper.toDto(post, comments.toArray(new Comment[0])));
		}
		return Optional.empty();
	}

}
