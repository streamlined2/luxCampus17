package org.training.campus.blog.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.training.campus.blog.model.Post;

@Repository
public interface PostDao extends JpaRepository<Post, Long> {

	List<Post> findAll();

	<T extends Post> T save(Post post);

	Optional<Post> findById(Long id);

	void deleteById(Long id);

}
