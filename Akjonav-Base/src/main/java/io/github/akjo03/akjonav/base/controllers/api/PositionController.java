package io.github.akjo03.akjonav.base.controllers.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tags(@Tag(name = "Position API"))
@RestController
@RequestMapping("/api/position")
public class PositionController {
	@GetMapping("/distance")
	public ResponseEntity<Double> getDistance() {
		return ResponseEntity.ok(0.0);
	}
}