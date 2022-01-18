package org.training.campus.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.training.campus.blog.model.Comment;

@Repository
public interface CommentDao extends JpaRepository<Comment, Long> {

}
