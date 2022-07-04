package io.github.akjo03.akjonav.base.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "auth0")
@Getter
@Setter
public class AuthProperties {
	private String domain;
	private String audience;
	private String clientId;
	private String clientSecret;
}