package org.training.campus.blog.dto;

import org.springframework.stereotype.Component;
import org.training.campus.blog.model.Tag;

@Component
public class TagMapper {

	public TagDto toDto(Tag tag) {
		return new TagDto(tag.getId(), tag.getName());
	}

	public Tag toTag(TagDto dto) {
		return Tag.builder().id(dto.id()).name(dto.name()).build();
	}

}
