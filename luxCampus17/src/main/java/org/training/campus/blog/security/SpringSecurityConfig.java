package org.training.campus.blog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.training.campus.blog.model.User;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String POSTS_MAPPING = "api/v1/posts/**";
	private static final String COMMENTS_MAPPING = "api/v1/posts/{[0-9]+}/comments/**";

	private final BasicAuthProvider authProvider;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}

	@Configuration
	@Order(1)
	public static class UserSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.csrf().disable()
				.requestMatcher(new OrRequestMatcher(
						new AntPathRequestMatcher(COMMENTS_MAPPING),
						new AntPathRequestMatcher(POSTS_MAPPING, HttpMethod.GET.name())
				))
				.authorizeRequests().anyRequest().hasAuthority(User.Role.USER.name())
				.and().httpBasic().authenticationEntryPoint(userAuthenticationEntryPoint());
		}

		@Bean
		public AuthenticationEntryPoint userAuthenticationEntryPoint() {
			var entryPoint = new BasicAuthenticationEntryPoint();
			entryPoint.setRealmName("user realm");
			return entryPoint;
		}
	}

	@Configuration
	@Order(2)
	public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.csrf().disable()
				.requestMatcher(new AntPathRequestMatcher(POSTS_MAPPING))
				.authorizeRequests().anyRequest().hasAuthority(User.Role.ADMIN.name())
				.and().httpBasic().authenticationEntryPoint(adminAuthenticationEntryPoint());
		}

		@Bean
		public AuthenticationEntryPoint adminAuthenticationEntryPoint() {
			var entryPoint = new BasicAuthenticationEntryPoint();
			entryPoint.setRealmName("admin realm");
			return entryPoint;
		}
	}

	@Configuration
	@Order(3)
	public static class EverybodySecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.csrf().disable()
				.requestMatcher(new OrRequestMatcher(
						new AntPathRequestMatcher("api/v1/posts/**", HttpMethod.GET.name()),
						new AntPathRequestMatcher("api/v1/tags/**")))
				.authorizeRequests()
				.anyRequest().permitAll();
		}
	}

}
