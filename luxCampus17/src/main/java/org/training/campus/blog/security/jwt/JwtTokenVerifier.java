package org.training.campus.blog.security.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtTokenVerifier extends OncePerRequestFilter {

	private final JwtConfig jwtConfig;
	private final SecretKey secretKey;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());

		if (Strings.isBlank(authorizationHeader) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {

			filterChain.doFilter(request, response);
			return;

		}

		String token = authorizationHeader.substring(jwtConfig.getTokenPrefix().length());
		try {
			Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);

			Claims body = claims.getBody();

			String username = body.getSubject();

			var authorities = (List<Map<String, String>>) body.get("authorities");
			Set<SimpleGrantedAuthority> simpleGrantedAuthority = authorities.stream()
					.map(map -> new SimpleGrantedAuthority(map.get("authority"))).collect(Collectors.toSet());

			Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,
					simpleGrantedAuthority);

			SecurityContextHolder.getContext().setAuthentication(authentication);

			filterChain.doFilter(request, response);

		} catch (JwtException e) {
			throw new IllegalStateException(String.format("token %s cannot be trusted", token));
		}

	}

}
