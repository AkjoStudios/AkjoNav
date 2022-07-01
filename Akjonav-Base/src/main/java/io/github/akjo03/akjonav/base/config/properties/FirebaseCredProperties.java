package io.github.akjo03.akjonav.base.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "firebase.credential")
@Getter
@Setter
public class FirebaseCredProperties {
	private String path;
}