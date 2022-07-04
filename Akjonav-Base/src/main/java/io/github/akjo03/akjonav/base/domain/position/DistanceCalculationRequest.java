package io.github.akjo03.akjonav.base.domain.position;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(name = "DistanceCalculationRequest", description = "Request for calculating the distance between two positions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class DistanceCalculationRequest {
	@Schema(name = "start_position", description = "Start Position")
	@JsonProperty("start_position")
	private Object startPosition;

	@Schema(name = "end_position", description = "End Position")
	@JsonProperty("end_position")
	private Object endPosition;
}