package org.training.campus.blog.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor @NoArgsConstructor @Builder
@Getter @Setter
@ToString @EqualsAndHashCode(of = { "id" })
@Entity @Table(name = "post", schema = "public")
public class Post implements Serializable {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NaturalId @Column(name = "title", nullable = false) @JsonProperty
	private String title;
	@Column(name = "content", nullable = false) @JsonProperty
	private String content;
	@Column(name = "star", nullable = false) @JsonProperty
	private boolean star;
	@OneToMany(mappedBy = "post_id")
	private List<Comment> comments;

}
