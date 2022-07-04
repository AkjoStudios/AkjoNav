package io.github.akjo03.akjonav.base.config;

import io.github.akjo03.akjonav.base.constants.AkjonavBaseConstants;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "springdoc.swagger-ui.enabled", havingValue = "true", matchIfMissing = true)
@SecurityScheme(
		name = "Bearer Authentication",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "Bearer"
)
public class OpenApiConfig {
	@Bean
	public OpenAPI getOpenAPI() {
		return new OpenAPI()
				.info(getInfo());
	}

	private Info getInfo() {
		return new Info()
				.title(AkjonavBaseConstants.MODULE_NAME + " API")
				.description("This is the API to interact with the Akjonav-Model module.")
				.version(AkjonavBaseConstants.APP_VERSION)
				.contact(new Contact().name("AkjoStudios").email("akjostudios@gmx.net").url("https://akjo03.github.io"))
				.license(new License().name("GNU General Public License v3.0").url("https://choosealicense.com/licenses/gpl-3.0/"));
	}
}