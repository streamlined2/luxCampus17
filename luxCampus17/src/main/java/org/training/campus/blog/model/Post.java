package org.training.campus.blog.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	@NaturalId @Column(nullable = false) @JsonProperty
	private String title;
	@Column(nullable = false) @JsonProperty
	private String content;
	@Column @JsonProperty
	private boolean star;

}
