package org.training.campus.blog.security.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter @Setter
public class UsernamePasswordAuthRequest {
	
	private String username;
	private String password;

}
