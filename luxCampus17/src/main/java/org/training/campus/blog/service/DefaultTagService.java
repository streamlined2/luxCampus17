package org.training.campus.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.training.campus.blog.dao.TagDao;
import org.training.campus.blog.dto.TagDto;
import org.training.campus.blog.dto.TagMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
public class DefaultTagService implements TagService {

	private final TagDao tagDao;
	private final TagMapper tagMapper;

	@Override
	public List<TagDto> findAll() {
		return tagDao.findAll().stream().map(tagMapper::toDto).toList();
	}

	@Override
	public Optional<TagDto> findById(Long id) {
		return tagDao.findById(id).map(tagMapper::toDto);
	}

}
