package io.github.akjo03.akjonav.model.util.position;

import io.github.akjo03.akjonav.model.services.JsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = { JsonService.class })
public class AkjonavPositionTest {
	private final JsonService jsonService;

	@Autowired
	public AkjonavPositionTest(JsonService jsonService) {
		this.jsonService = jsonService;
	}
}