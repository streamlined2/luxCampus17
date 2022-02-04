package org.training.campus.blog.model;

import java.io.Serializable;
import java.util.List;

import static org.training.campus.blog.LuxCampus17Application.DB_SCHEMA_NAME;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
@Entity @Table(name = "tag", schema = DB_SCHEMA_NAME)
@SequenceGenerator(name = "tag_generator", schema = DB_SCHEMA_NAME, sequenceName = "tag_generator")
public class Tag implements Serializable {
		
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_generator")
	private long id;
	
	@NaturalId @Length(min = 1, max = 20, message = "length of name should be within 1..20") @NotBlank
	@Column(name = "name", length = 20, unique = true, nullable = false)
	private String name;
	
	@ManyToMany(mappedBy = "tags")
	private List<Post> posts;

}
