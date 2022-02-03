package org.training.campus.blog.model;

import java.io.Serializable;
import static org.training.campus.blog.LuxCampus17Application.DB_SCHEMA_NAME;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Builder
@Getter @Setter
@EqualsAndHashCode(of = { "id" })
@Entity @Table(name = "post", schema = DB_SCHEMA_NAME)
@SequenceGenerator(name = "post_generator", schema = DB_SCHEMA_NAME, sequenceName = "post_generator")
public class Post implements Serializable {

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_generator")
	private long id;
	
	@NaturalId @Column(name = "title", nullable = false) @Length(min = 1, max = 100, message = "length of title should be within 1..100") @NotBlank
	private String title;
	
	@Column(name = "content", nullable = false) @Length(min = 1, max = 10000, message = "length of content should be within 1..10000") @NotBlank
	private String content;
	
	@Column(name = "star", nullable = false)
	private boolean star;
	
	@OneToMany(mappedBy = "post")
	private List<Comment> comments;
	
	@ManyToMany
	@JoinTable(name = "posts_tags", schema = DB_SCHEMA_NAME, 
		joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private Set<Tag> tags;
	
}
