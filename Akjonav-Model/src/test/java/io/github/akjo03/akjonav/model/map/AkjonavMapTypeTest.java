package io.github.akjo03.akjonav.model.map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AkjonavMapTypeTest {
	@Test
	void testGetTypeID() {
		assertEquals("AkjonavMap", AkjonavMapType.type.getTypeID());
	}

	@Test
	void testGetBuilder() {
		assertEquals(AkjonavMapBuilder.class, AkjonavMapType.type.getBuilder().getClass());
	}

	@Test
	void testToString() {
		assertEquals("AkjonavMap", AkjonavMapType.type.toString());
	}
}