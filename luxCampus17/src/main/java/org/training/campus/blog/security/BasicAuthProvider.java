package org.training.campus.blog.security;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.training.campus.blog.dao.UserDao;
import org.training.campus.blog.model.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BasicAuthProvider implements AuthenticationProvider {

	private final UserDao userDao;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userName = authentication.getName();
		Optional<User> userData = userDao.findByName(userName);
		if (userData.isEmpty()) {
			throw new UsernameNotFoundException(String.format("no such user %s found", userName));
		}
		String password = authentication.getCredentials().toString();
		if (!passwordEncoder.matches(password, userData.get().getEncodedPassword())) {
			throw new BadCredentialsException(String.format("wrong credentials for user %s", userName));
		}
		return new UsernamePasswordAuthenticationToken(userName, password,
				List.of(new SimpleGrantedAuthority(userData.get().getRole().name())));
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
