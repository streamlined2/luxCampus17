package org.training.campus.blog.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.training.campus.blog.model.Post;

@Repository
public interface PostDao extends JpaRepository<Post, Long> {

	@Query("SELECT DISTINCT p FROM Post p LEFT JOIN p.tags t WHERE t.id IN :tagIds")
	List<Post> findPostsByTags(@Param("tagIds") Set<Long> tagIds);

}
