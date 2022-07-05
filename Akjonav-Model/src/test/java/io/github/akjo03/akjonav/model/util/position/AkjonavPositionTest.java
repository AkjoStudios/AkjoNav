package io.github.akjo03.akjonav.model.util.position;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.services.JsonService;
import io.github.akjo03.util.math.unit.units.length.Length;
import io.github.akjo03.util.math.unit.units.length.LengthUnit;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AkjonavPositionTest {
	private final JsonService jsonService;

	protected AkjonavPositionTest() {
		this.jsonService = new JsonService();
	}

	@Test
	void testValidSerializationWithoutAltitude() {
		AkjonavPosition position = new AkjonavPositionBuilder(0.0, 0.0).build();
		ObjectNode objectNode = position.serialize(jsonService.getObjectMapper());
		assertEquals(AkjonavPositionType.type.getTypeID(), objectNode.get("type").asText());
		assertNotNull(objectNode.get("data"));
		assertEquals(0.0, objectNode.get("data").get("lat").asDouble());
		assertEquals(0.0, objectNode.get("data").get("lon").asDouble());
		assertNull(objectNode.get("data").get("alt"));
	}

	@Test
	void testValidSerializationWithAltitude() {
		AkjonavPosition position = new AkjonavPositionBuilder(1.0, 2.0)
				.withAltitude(new Length(new BigDecimal("3.0"), LengthUnit.METRE))
				.build();
		ObjectNode objectNode = position.serialize(jsonService.getObjectMapper());
		assertEquals(AkjonavPositionType.type.getTypeID(), objectNode.get("type").asText());
		assertNotNull(objectNode.get("data"));
		assertEquals(1.0, objectNode.get("data").get("lat").asDouble());
		assertEquals(2.0, objectNode.get("data").get("lon").asDouble());
		assertNotNull(objectNode.get("data").get("alt"));
		assertEquals(new BigDecimal("3.0"), new BigDecimal(objectNode.get("data").get("alt").get("value").asText()));
		assertEquals(LengthUnit.METRE, LengthUnit.getUnit(objectNode.get("data").get("alt").get("unit").asText()));
	}

	@Test
	void testToStringWithoutAltitude() {
		AkjonavPosition position = new AkjonavPositionBuilder(1.0, 2.0).build();
		assertEquals("AkjonavPosition{type=AkjonavPosition, data={latitude=1.0, longitude=2.0, altitude=null}}", position.toString());
	}

	@Test
	void testToStringWithAltitude() {
		AkjonavPosition position = new AkjonavPositionBuilder(1.0, 2.0)
				.withAltitude(new Length(new BigDecimal("3.0"), LengthUnit.METRE))
				.build();
		assertEquals("AkjonavPosition{type=AkjonavPosition, data={latitude=1.0, longitude=2.0, altitude=3m}}", position.toString());
	}

	@Test
	void testEqualsWithoutAltitude() {
		AkjonavPosition position1 = new AkjonavPositionBuilder(1.0, 2.0).build();
		AkjonavPosition position2 = new AkjonavPositionBuilder(1.0, 2.0).build();
		assertEquals(position1, position2);
	}

	@Test
	void testEqualsWithAltitude() {
		AkjonavPosition position1 = new AkjonavPositionBuilder(1.0, 2.0)
				.withAltitude(new Length(new BigDecimal("3.0"), LengthUnit.METRE))
				.build();
		AkjonavPosition position2 = new AkjonavPositionBuilder(1.0, 2.0)
				.withAltitude(new Length(new BigDecimal("3.0"), LengthUnit.METRE))
				.build();
		assertEquals(position1, position2);
	}
}