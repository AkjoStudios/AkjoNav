package io.github.akjo03.akjonav.desktopmapper.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AppWindowSettings {
	@JsonProperty("width")
	private String width;
	@JsonProperty("height")
	private String height;
	@JsonProperty("screenX")
	private String screenX;
	@JsonProperty("screenY")
	private String screenY;
	@JsonProperty("maximized")
	private String maximized;
	@JsonProperty("theme")
	private String theme;
}