package io.github.akjo03.akjonav.model.util.position;

import io.github.akjo03.akjonav.model.services.JsonService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = { JsonService.class })
class AkjonavPositionTypeTest {
	@Test
	void testGetTypeID() {
		assertEquals("AkjonavPosition", AkjonavPositionType.type.getTypeID());
	}

	@Test
	void testGetBuilder() {
		assertEquals(AkjonavPositionBuilder.class, AkjonavPositionType.type.getBuilder().getClass());
	}

	@Test
	void testToString() {
		assertEquals("AkjonavPosition", AkjonavPositionType.type.toString());
	}
}