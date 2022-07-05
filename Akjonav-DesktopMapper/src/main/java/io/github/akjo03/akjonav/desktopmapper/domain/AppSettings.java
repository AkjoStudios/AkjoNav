package io.github.akjo03.akjonav.desktopmapper.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AppSettings {
	@JsonProperty("version")
	private String version;
	@JsonProperty("window")
	private AppWindowSettings windowSettings;
}