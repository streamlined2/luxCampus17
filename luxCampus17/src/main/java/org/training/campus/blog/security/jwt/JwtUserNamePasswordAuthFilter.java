package org.training.campus.blog.security.jwt;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtUserNamePasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authManager;
	private final JwtConfig jwtConfig;
	private final SecretKey secretKey;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			UsernamePasswordAuthRequest authRequest = new ObjectMapper().readValue(request.getInputStream(),
					UsernamePasswordAuthRequest.class);
			Authentication authToken = new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
					authRequest.getPassword());
			return authManager.authenticate(authToken);
		} catch (IOException e) {
			throw new UsernameNotFoundException("can't get credentials from token", e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String token = Jwts.builder().setSubject(authResult.getName()).claim("authorities", authResult.getAuthorities())
				.setIssuedAt(Date.from(Instant.now())).setExpiration(Date.from(Instant.now().plus(Duration.ofDays(7))))
				.signWith(secretKey).compact();
		response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token);
	}

}
