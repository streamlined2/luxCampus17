package org.training.campus.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.stereotype.Service;
import org.training.campus.blog.dao.PostDao;
import org.training.campus.blog.model.Post;
import org.training.campus.blog.model.Post.PostBuilder;

@Service
public class PostService {

	@Autowired
	private PostDao dao;

	public List<Post> findAll() {
		return dao.findAll();
	}

	public List<Post> findAll(Optional<String> title, Optional<String> sortProperty) {
		PostBuilder builder = Post.builder();
		title.ifPresent(builder::title);
		var matcher = ExampleMatcher.matchingAll().withMatcher("title", GenericPropertyMatchers.contains().ignoreCase())
				.withIgnorePaths("id");
		Example<Post> example = Example.of(builder.build(), matcher);
		return dao.findAll(example);
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
