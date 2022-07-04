package io.github.akjo03.akjonav.base.controllers.api;

import io.github.akjo03.akjonav.base.constants.AkjonavBaseConstants;
import io.github.akjo03.akjonav.base.domain.auth.AuthLoginRequest;
import io.github.akjo03.akjonav.base.domain.auth.AuthLoginResponse;
import io.github.akjo03.util.logging.v2.Logger;
import io.github.akjo03.util.logging.v2.LoggerManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tags(@Tag(name = "Auth API"))
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private static final Logger LOGGER = LoggerManager.getLogger(AuthController.class)
			.setMinimumLoggingLevel(AkjonavBaseConstants.LOGGING_LEVEL)
			.setLoggingFormat(AkjonavBaseConstants.LOGGING_FORMAT);

	@Value("${auth0.audience}")
	private String audience;

	@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
	private String issuer;

	@PostMapping("/login")
	@Operation(
			summary = "Login to AkjoNav"
	)
	public ResponseEntity<AuthLoginResponse> login(@RequestBody @NotNull AuthLoginRequest request) {
		LOGGER.info(request);
		AuthLoginResponse response = new RestTemplateBuilder().build()
				.postForObject(
					issuer + "/oauth/token",
					request,
					AuthLoginResponse.class
				);
		return ResponseEntity.ok(response);
	}
}