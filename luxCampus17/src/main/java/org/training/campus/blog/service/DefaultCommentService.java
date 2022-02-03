package org.training.campus.blog.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.training.campus.blog.dao.CommentDao;
import org.training.campus.blog.dao.PostDao;
import org.training.campus.blog.dto.CommentDto;
import org.training.campus.blog.dto.CommentMapper;
import org.training.campus.blog.model.Comment;
import org.training.campus.blog.model.Post;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
public class DefaultCommentService implements CommentService {

	private final PostDao postDao;
	private final CommentDao commentDao;
	private final CommentMapper commentMapper;

	private Stream<Comment> getStreamOfCommentsForPost(Long postId) {
		return postDao.findById(postId).map(Post::getComments).orElse(List.of()).stream();
	}

	@Override
	public List<CommentDto> getCommentsForPost(Long postId) {
		return getStreamOfCommentsForPost(postId).map(commentMapper::toDto).toList();
	}

	@Override
	public Optional<CommentDto> getCommentForPost(Long postId, Long commentId) {
		return getStreamOfCommentsForPost(postId).filter((Comment comment) -> comment.getId() == commentId).findAny()
				.map(commentMapper::toDto);
	}

	@Override
	@Transactional(readOnly = false)
	public Optional<CommentDto> add(Long postId, CommentDto commentDto) {
		Optional<Post> postData = postDao.findById(postId);
		if (postData.isPresent()) {
			Comment comment = commentMapper.toComment(commentDto);
			comment.setPost(postData.get());
			comment.setCreationDate(LocalDate.now());
			return Optional.of(commentMapper.toDto(commentDao.save(comment)));
		}
		return Optional.empty();
	}

	@Override
	@Transactional(readOnly = false)
	public Optional<CommentDto> save(Long commentId, CommentDto commentDto) {
		Optional<Comment> commentData = commentDao.findById(commentId);
		if (commentData.isPresent()) {
			Comment comment = commentData.get();
			comment.setText(commentDto.text());
			return Optional.of(commentMapper.toDto(commentDao.save(comment)));
		}
		return Optional.empty();
	}

}
