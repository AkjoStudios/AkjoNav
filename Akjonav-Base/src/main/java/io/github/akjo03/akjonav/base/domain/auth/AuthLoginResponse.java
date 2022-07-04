package io.github.akjo03.akjonav.base.domain.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(name = "AuthLoginRequest", description = "Response after logging into an Akjonav API")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AuthLoginResponse {
	@Schema(name = "access_token", description = "Access Token")
	@JsonProperty("access_token")
	private String accessToken;
	@Schema(name = "expires_in", description = "Expires In")
	@JsonProperty("expires_in")
	private Integer expiresIn;
	@Schema(name = "token_type", description = "Token Type")
	@JsonProperty("token_type")
	private String token_type;
}