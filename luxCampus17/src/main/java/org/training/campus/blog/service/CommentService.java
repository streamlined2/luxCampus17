package org.training.campus.blog.service;

import java.util.List;
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

}
