package io.github.akjo03.akjonav.base.controllers.api;

import io.github.akjo03.akjonav.base.constants.AkjonavBaseConstants;
import io.github.akjo03.akjonav.base.domain.position.DistanceCalculationRequest;
import io.github.akjo03.akjonav.base.domain.position.DistanceCalculationResponse;
import io.github.akjo03.akjonav.base.services.ModelConverterService;
import io.github.akjo03.akjonav.model.util.position.AkjonavPosition;
import io.github.akjo03.akjonav.model.util.position.AkjonavPositionBuilder;
import io.github.akjo03.util.logging.v2.Logger;
import io.github.akjo03.util.logging.v2.LoggerManager;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Tags(@Tag(name = "Position API"))
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/position")
public class PositionController {
	private static final Logger LOGGER = LoggerManager.getLogger(PositionController.class)
			.setMinimumLoggingLevel(AkjonavBaseConstants.LOGGING_LEVEL)
			.setLoggingFormat(AkjonavBaseConstants.LOGGING_FORMAT);

	private final ModelConverterService modelConverterService;

	@PostMapping("/distance")
	@SecurityRequirement(name = "Bearer Authentication")
	public ResponseEntity<DistanceCalculationResponse> getDistance(@RequestBody @NotNull DistanceCalculationRequest request) {
		AkjonavPosition startPosition = modelConverterService.convertData((HashMap<?, ?>) request.getStartPosition(), new AkjonavPositionBuilder());
		AkjonavPosition endPosition = modelConverterService.convertData((HashMap<?, ?>) request.getEndPosition(), new AkjonavPositionBuilder());



		return ResponseEntity.ok(new DistanceCalculationResponse(0.0));
	}
}