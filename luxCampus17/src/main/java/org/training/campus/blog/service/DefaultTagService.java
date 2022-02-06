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
import org.training.campus.blog.model.Tag;

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

	@Override
	@Transactional
	public TagDto save(TagDto tagDto) {
		return tagMapper.toDto(tagDao.save(tagMapper.toTag(tagDto)));
	}

	@Override
	@Transactional
	public TagDto save(Long id, TagDto tagDto) {
		Tag tag = tagMapper.toTag(tagDto);
		tag.setId(id);
		return tagMapper.toDto(tagDao.save(tag));
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		tagDao.deleteById(id);
	}

}
