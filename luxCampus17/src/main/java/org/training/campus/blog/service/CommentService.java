package org.training.campus.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.training.campus.blog.dao.CommentDao;

@Service
public class CommentService {
	
	private final CommentDao dao;
	
	@Autowired
	private CommentService(CommentDao dao) {
		this.dao = dao;
	}

}
