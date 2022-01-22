package org.training.campus.blog.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Builder
@Getter @Setter
@EqualsAndHashCode(of = { "id" })
@Entity @Table(name = "post", schema = "public")
public class Post implements Serializable {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NaturalId @Column(name = "title", nullable = false)
	private String title;
	@Column(name = "content", nullable = false)
	private String content;
	@Column(name = "star", nullable = false)
	private boolean star;
	@OneToMany(mappedBy = "post")
	private final List<Comment> comments = new ArrayList<>();
	
}
