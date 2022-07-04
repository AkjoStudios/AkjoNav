package io.github.akjo03.akjonav.base.domain.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(name = "AuthLoginRequest", description = "Request for logging into an Akjonav API", hidden = true)
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AuthLoginRequest {
	@Schema(name = "client_id", description = "Client ID")
	@JsonProperty("client_id")
	private String clientID;
	@Schema(name = "client_secret", description = "Client Secret")
	@JsonProperty("client_secret")
	private String clientSecret;
	@Schema(name = "audience", description = "Audience", defaultValue = "api.akjonav.io")
	@JsonProperty("audience")
	private String audience;
	@Schema(name = "grant_type", description = "Grant Type", defaultValue = "client_credentials")
	@JsonProperty("grant_type")
	private String grantType;
}