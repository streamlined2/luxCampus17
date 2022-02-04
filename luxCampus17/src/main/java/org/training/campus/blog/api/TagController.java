package org.training.campus.blog.api;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.training.campus.blog.dto.TagDto;
import org.training.campus.blog.service.TagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/tags")
public class TagController {

	private final TagService tagService;

	@GetMapping
	public List<TagDto> getAll() {
		return tagService.findAll();
	}

	@GetMapping("/{id}")
	public TagDto getOne(@PathVariable Long id) {
		return tagService.findById(id).orElse(null);
	}

	@PostMapping
	public Long add(@RequestBody TagDto tagDto) {
		return tagService.save(tagDto).id();
	}

	@PutMapping("/{id}")
	public void modify(@PathVariable Long id, @RequestBody TagDto tagDto) {
		tagService.save(id, tagDto);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		tagService.deleteById(id);
	}

}
