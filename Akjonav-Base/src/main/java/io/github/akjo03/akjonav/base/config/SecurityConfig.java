package io.github.akjo03.akjonav.base.config;

import io.github.akjo03.akjonav.base.model.RestUser;
import io.github.akjo03.akjonav.base.persistence.RestUserRepository;
import io.github.akjo03.util.logging.v2.Logger;
import io.github.akjo03.util.logging.v2.LoggerManager;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends BasicAuthenticationEntryPoint implements AuthenticationManager {
	private static final Logger LOGGER = LoggerManager.getLogger("SecurityManager");
	private final RestUserRepository restUserRepository;

	@Bean
	public SecurityFilterChain filterChain(@NotNull HttpSecurity http) throws Exception {
		return http
				.authorizeRequests()
				.antMatchers(
						"/",
						"/swagger-ui.html",
						"/swagger-ui/**",
						"/api-docs",
						"/api-docs/**"
				).permitAll()
				.antMatchers("/**").authenticated()
				.and().httpBasic()
				.authenticationEntryPoint(this)
				.and().build();
	}

	@Override
	public Authentication authenticate(@NotNull Authentication authentication) throws AuthenticationException {
		String username = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();

		RestUser restUser = restUserRepository.getByUsername(username);
		if (restUser == null) {
			UsernameNotFoundException e = new UsernameNotFoundException("User not found");
			LOGGER.error(e.getMessage(), e);
			throw e;
		}
		if (!restUser.getPassword().equals(password)) {
			BadCredentialsException e = new BadCredentialsException("Password is incorrect");
			LOGGER.error(e.getMessage(), e);
			throw e;
		}

		List<GrantedAuthority> grantedAuths = new ArrayList<>();
		for (String role : restUser.getRoles()) {
			grantedAuths.add(new SimpleGrantedAuthority(role));
		}
		return new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
	}

	@Override
	public void commence(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull AuthenticationException authException) throws IOException {
		response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		PrintWriter writer = response.getWriter();
		writer.println("HTTP Status 401 - " + authException.getMessage());
	}

	@Override
	public void afterPropertiesSet() {
		setRealmName("AkjoNav");
		super.afterPropertiesSet();
	}
}