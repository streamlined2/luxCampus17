package org.training.campus.blog.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.training.campus.blog.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
	
	Optional<User> findByName(String name);

}
