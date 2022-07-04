package io.github.akjo03.akjonav.base.domain.position;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(name = "DistanceCalculationResponse", description = "Response for calculating the distance between two positions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class DistanceCalculationResponse {
	@Schema(name = "distance", description = "Distance")
	@JsonProperty("distance")
	private Double distance;
}