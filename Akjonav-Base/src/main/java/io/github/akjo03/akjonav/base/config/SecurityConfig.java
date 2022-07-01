package io.github.akjo03.akjonav.base.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements AuthenticationManager {
	@Bean
	public SecurityFilterChain filterChain(@NotNull HttpSecurity http) throws Exception {
		return http
				.csrf().disable()
				.authorizeRequests()
				.antMatchers(
						"/",
						"/swagger-ui.html",
						"/swagger-ui/**",
						"/api-docs",
						"/api-docs/**"
				).permitAll()
				.and().httpBasic()
				.and().build();
	}

	@Override
	public Authentication authenticate(@NotNull Authentication authentication) throws AuthenticationException {
		String username = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();

		// TODO: Implement authentication

		List<GrantedAuthority> grantedAuths = new ArrayList<>();
		grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
		return new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
	}
}