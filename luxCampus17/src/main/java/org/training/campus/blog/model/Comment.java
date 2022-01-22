package org.training.campus.blog.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Builder
@Setter @Getter
@EqualsAndHashCode(of= {"id"})
@Entity @Table(name = "comment", schema = "public")
public class Comment implements Serializable {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "text", nullable = false)
	private String text;
	@Column(name = "creation_date", nullable = false)
	private LocalDate creationDate;
	@ManyToOne(optional = false) @JoinColumn(nullable = false, name = "post_id")
	private Post post;

}
