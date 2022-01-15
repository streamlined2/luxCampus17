package org.training.campus.blog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.training.campus.blog.dao.PostDao;
import org.training.campus.blog.model.Post;

@Service
public class PostService {

	@Autowired
	private PostDao dao;

	public Iterable<Post> findAll() {
		return dao.findAll();
	}

	public <T extends Post> T save(Post post) {
		return dao.save(post);
	}

	public Optional<Post> findById(Long id) {
		return dao.findById(id);
	}

	public void deleteById(Long id) {
		dao.deleteById(id);
	}

}
