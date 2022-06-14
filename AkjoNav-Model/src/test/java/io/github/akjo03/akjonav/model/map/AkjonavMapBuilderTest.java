package io.github.akjo03.akjonav.model.map;

import io.github.akjo03.akjonav.model.services.JsonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration(classes = { JsonService.class })
class AkjonavMapBuilderTest {
	private final JsonService jsonService;

	@Autowired
	public AkjonavMapBuilderTest(JsonService jsonService) {
		this.jsonService = jsonService;
	}

	@Test
	void testEmptyConstructor() {
		AkjonavMapBuilder builder = new AkjonavMapBuilder();
		assertNotNull(builder.getBaseElements());
		assertEquals(0, builder.getBaseElements().size());
		assertNotNull(builder.getMapElements());
		assertEquals(0, builder.getMapElements().size());
		assertNotNull(builder.getElementReferences());
		assertEquals(0, builder.getElementReferences().size());
	}
}