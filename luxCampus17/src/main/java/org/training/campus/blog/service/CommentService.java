package org.training.campus.blog.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.training.campus.blog.dao.CommentDao;
import org.training.campus.blog.dao.PostDao;
import org.training.campus.blog.model.Comment;
import org.training.campus.blog.model.Post;

@Service
public class CommentService {

	private final PostDao postDao;
	private final CommentDao commentDao;

	@Autowired
	private CommentService(PostDao postDao, CommentDao commentDao) {
		this.postDao = postDao;
		this.commentDao = commentDao;
	}

	public List<Comment> getCommentsForPost(Long postId) {
		return postDao.findById(postId).map(Post::getComments).orElse(List.of());
	}

	public Optional<Comment> getCommentForPost(Long postId, Long commentId) {
		return getCommentsForPost(postId).stream().filter(comment -> comment.getId() == commentId).findAny();
	}

	public Optional<Comment> save(Long postId, Comment comment) {
		Optional<Post> post = postDao.findById(postId);
		if (post.isPresent()) {
			comment.setPost(post.get());
			comment.setCreationDate(LocalDate.now());
			return Optional.of(commentDao.save(comment));
		}
		return Optional.empty();
	}

}
