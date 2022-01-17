package org.training.campus.blog.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.training.campus.blog.dao.PostDao;
import org.training.campus.blog.model.Post;

@Service
public class PostService {

	@Autowired
	private PostDao dao;

	public List<Post> findAll() {
		return dao.findAll();
	}

	public List<Post> findAll(Map<String, String> filterMap, List<String> sortList) {
		return dao.findAll();
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

}
