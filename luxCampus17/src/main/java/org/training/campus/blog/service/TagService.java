package org.training.campus.blog.service;

import java.util.List;
import java.util.Optional;
import org.training.campus.blog.dto.TagDto;

public interface TagService {
	
	List<TagDto> findAll();

	Optional<TagDto> findById(Long id);

}
