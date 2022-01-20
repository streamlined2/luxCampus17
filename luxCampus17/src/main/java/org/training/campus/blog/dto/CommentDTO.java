package org.training.campus.blog.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public record CommentDTO(long id, String text,
		@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd") LocalDate creationDate) {
}
