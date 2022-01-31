package org.training.campus.blog.model;

import java.io.Serializable;
import static org.training.campus.blog.LuxCampus17Application.DB_SCHEMA_NAME;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Builder
@Setter @Getter
@EqualsAndHashCode(of= {"id"})
@Entity @Table(name = "comment", schema = DB_SCHEMA_NAME)
@SequenceGenerator(name = "comment_generator", schema = DB_SCHEMA_NAME, sequenceName = "comment_generator")
public class Comment implements Serializable {
	
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_generator")
	private long id;
	@Column(name = "text", nullable = false) @Length(min = 1, max = 10000) @NotBlank
	private String text;
	@Column(name = "creation_date", nullable = false) @PastOrPresent
	private LocalDate creationDate;
	@ManyToOne(optional = false) @JoinColumn(nullable = false, name = "post_id")
	private Post post;

}
