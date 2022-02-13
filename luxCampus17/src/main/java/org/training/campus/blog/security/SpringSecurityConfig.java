package org.training.campus.blog.security;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.training.campus.blog.model.User;
import org.training.campus.blog.security.jwt.JwtConfig;
import org.training.campus.blog.security.jwt.JwtTokenVerifier;
import org.training.campus.blog.security.jwt.JwtUserNamePasswordAuthFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@Order(0)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String POSTS_MAPPING = "/api/v1/posts/**";
	private static final String COMMENTS_MAPPING = "/api/v1/posts/{[0-9]+}/comments/**";

	private final BasicAuthProvider authProvider;
	private final SecretKey secretKey;
	private final JwtConfig jwtConfig;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.addFilter(new JwtUserNamePasswordAuthFilter(authenticationManager(), jwtConfig, secretKey))
				.addFilterAfter(new JwtTokenVerifier(jwtConfig, secretKey), JwtUserNamePasswordAuthFilter.class);
	}

	@Configuration
	@Order(1)
	public static class UserSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.requestMatcher(new OrRequestMatcher(new AntPathRequestMatcher(COMMENTS_MAPPING),
					new AntPathRequestMatcher(POSTS_MAPPING, HttpMethod.GET.name()))).authorizeRequests().anyRequest()
					.hasAuthority(User.Role.USER.name());
		}

	}

	@Configuration
	@Order(2)
	public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.requestMatcher(new AntPathRequestMatcher(POSTS_MAPPING)).authorizeRequests().anyRequest()
					.hasAuthority(User.Role.ADMIN.name());
		}

	}

	@Configuration
	@Order(3)
	public static class EverybodySecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.requestMatcher(
					new OrRequestMatcher(new AntPathRequestMatcher("/api/v1/posts/**", HttpMethod.GET.name()),
							new AntPathRequestMatcher("/api/v1/tags/**")))
					.authorizeRequests().anyRequest().permitAll();
		}

	}

}
