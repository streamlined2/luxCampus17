package org.training.campus.blog.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = { "id" })
@ToString
public class Post implements Serializable {

	@Id
	@GeneratedValue
	@Setter(AccessLevel.PROTECTED)
	private long id;
	private @NonNull String title;
	private @NonNull String content;

}
