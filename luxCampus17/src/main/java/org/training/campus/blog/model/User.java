package org.training.campus.blog.model;

import java.io.Serializable;
import static org.training.campus.blog.LuxCampus17Application.DB_SCHEMA_NAME;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Entity @Table(name = "user", schema = DB_SCHEMA_NAME)
@SequenceGenerator(name = "user_generator", schema = DB_SCHEMA_NAME, sequenceName = "user_generator")
public class User implements Serializable {
	
	public enum Role { ADMIN, USER }

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
	private long id;
	
	@NaturalId @Column(name = "name", unique = true, length = 50) 
	@Length(min = 2, max = 50, message = "length of user name should be within [2..50]") @NotBlank
	private String name;
	
	@Column(name = "password", length = 100)
	@Length(min = 8, max = 100, message = "length of encoded password should be within [8..100]") @NotBlank
	private String encodedPassword;
	
	@Enumerated(EnumType.STRING) @Column(name = "role")
	private Role role;

}
