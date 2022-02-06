package org.training.campus.blog.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.RequestMethod;
import org.training.campus.blog.model.User;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	private final BasicAuthProvider authProvider;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().
		requestMatchers(new AntPathRequestMatcher("/api/v1/posts/**", RequestMethod.POST.name())).hasAuthority(User.Role.ADMIN.name()).
		requestMatchers(new AntPathRequestMatcher("/api/v1/posts/**", RequestMethod.PUT.name())).hasAuthority(User.Role.ADMIN.name()).
		requestMatchers(new AntPathRequestMatcher("/api/v1/posts/**", RequestMethod.DELETE.name())).hasAuthority(User.Role.ADMIN.name()).
		requestMatchers(new AntPathRequestMatcher("/api/v1/posts/*/comments/**", RequestMethod.POST.name())).hasAuthority(User.Role.USER.name()).
		requestMatchers(new AntPathRequestMatcher("/api/v1/posts/*/comments/**", RequestMethod.PUT.name())).hasAuthority(User.Role.USER.name()).
		requestMatchers(new AntPathRequestMatcher("/api/v1/posts/*/comments/**", RequestMethod.DELETE.name())).hasAuthority(User.Role.USER.name()).
		requestMatchers(new AntPathRequestMatcher("/api/v1/posts/**", RequestMethod.GET.name())).permitAll().
		requestMatchers(new AntPathRequestMatcher("/api/v1/tags/**")).permitAll().
		anyRequest().denyAll().
		and().httpBasic().
		and().csrf().disable();
	}

}
