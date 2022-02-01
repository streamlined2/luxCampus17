package org.training.campus.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.training.campus.blog.dao.TagDao;
import org.training.campus.blog.dto.TagDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
public class DefaultTagService implements TagService {

	private final TagDao dao;

	@Override
	public List<TagDto> findAll() {
		// TODO Auto-generated method stub
		return List.of();
	}

	@Override
	public Optional<TagDto> findById(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
