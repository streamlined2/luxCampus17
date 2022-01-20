package org.training.campus.blog.dto;

import java.time.LocalDate;

public record CommentDTO(long id, String text, LocalDate creationDate) {}
