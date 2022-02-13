package org.training.campus.blog.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "application.jwt")
@NoArgsConstructor @Getter @Setter
public class JwtConfig {

	private String secretKey;
	private String tokenPrefix;
	private Integer tokenExpirationAtferDays;

	public String getAuthorizationHeader() {
		return HttpHeaders.AUTHORIZATION;
	}

}
