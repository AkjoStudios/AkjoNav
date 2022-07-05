package io.github.akjo03.akjonav.model.util.position;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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