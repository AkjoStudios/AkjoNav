package io.github.akjo03.akjonav.model.util.position;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.services.JsonService;
import io.github.akjo03.util.math.unit.units.length.Length;
import io.github.akjo03.util.math.unit.units.length.LengthUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = { JsonService.class })
class AkjonavPositionBuilderTest {
	private final JsonService jsonService;

	@Autowired
	public AkjonavPositionBuilderTest(JsonService jsonService) {
		this.jsonService = jsonService;
	}

	@Test
	void testEmptyConstructor() {
		AkjonavPositionBuilder builder = new AkjonavPositionBuilder();
		assertNull(builder.getLatitude());
		assertNull(builder.getLongitude());
		assertNull(builder.getAltitude());
	}

	@Test
	void testConstructorWithPosition() {
		AkjonavPositionBuilder builder = new AkjonavPositionBuilder(1.0, 2.0);
		assertEquals(1.0, builder.getLatitude());
		assertEquals(2.0, builder.getLongitude());
		assertNull(builder.getAltitude());
	}

	@Test
	void testConstructorWithPositionAndAltitude() {
		AkjonavPositionBuilder builder = new AkjonavPositionBuilder(1.0, 2.0, new Length(new BigDecimal("3.0"), LengthUnit.METRE));
		assertEquals(1.0, builder.getLatitude());
		assertEquals(2.0, builder.getLongitude());
		assertNotNull(builder.getAltitude());
		assertEquals(new BigDecimal("3.0"), builder.getAltitude().getValue());
		assertEquals(LengthUnit.METRE, builder.getAltitude().getUnit());
	}

	@Test
	void testValidWithLatitude() {
		AkjonavPositionBuilder builder = new AkjonavPositionBuilder();
		builder.withLatitude(1.0);
		assertEquals(1.0, builder.getLatitude());
	}

	@Test
	void testNullWithLatitude() {
		AkjonavPositionBuilder builder = new AkjonavPositionBuilder();
		//noinspection ConstantConditions
		assertThrows(IllegalArgumentException.class, () -> builder.withLatitude(null));
	}

	@Test
	void testValidWithLongitude() {
		AkjonavPositionBuilder builder = new AkjonavPositionBuilder();
		builder.withLongitude(1.0);
		assertEquals(1.0, builder.getLongitude());
	}

	@Test
	void testNullWithLongitude() {
		AkjonavPositionBuilder builder = new AkjonavPositionBuilder();
		//noinspection ConstantConditions
		assertThrows(IllegalArgumentException.class, () -> builder.withLongitude(null));
	}

	@Test
	void testValidWithAltitude() {
		AkjonavPositionBuilder builder = new AkjonavPositionBuilder();
		builder.withAltitude(new Length(new BigDecimal("1.0"), LengthUnit.METRE));
		assertNotNull(builder.getAltitude());
		assertEquals(new BigDecimal("1.0"), builder.getAltitude().getValue());
		assertEquals(LengthUnit.METRE, builder.getAltitude().getUnit());
	}

	@Test
	void testChainability() {
		AkjonavPositionBuilder positionBuilder = new AkjonavPositionBuilder().withLatitude(1.0).withLongitude(2.0).withAltitude(new Length(new BigDecimal("3.0"), LengthUnit.METRE));
		assertEquals(1.0, positionBuilder.getLatitude());
		assertEquals(2.0, positionBuilder.getLongitude());
		assertNotNull(positionBuilder.getAltitude());
		assertEquals(new BigDecimal("3.0"), positionBuilder.getAltitude().getValue());
		assertEquals(LengthUnit.METRE, positionBuilder.getAltitude().getUnit());
	}

	@Test
	void testValidBuild() {
		AkjonavPositionBuilder positionBuilder = new AkjonavPositionBuilder().withLatitude(1.0).withLongitude(2.0).withAltitude(new Length(new BigDecimal("3.0"), LengthUnit.METRE));
		AkjonavPosition position = positionBuilder.build();
		assertEquals(1.0, position.getLatitude());
		assertEquals(2.0, position.getLongitude());
		assertNotNull(position.getAltitude());
		assertEquals(new BigDecimal("3.0"), position.getAltitude().getValue());
		assertEquals(LengthUnit.METRE, position.getAltitude().getUnit());
	}

	@Test
	void testNullLatitudeBuild() {
		AkjonavPositionBuilder positionBuilder = new AkjonavPositionBuilder().withLongitude(2.0).withAltitude(new Length(new BigDecimal("3.0"), LengthUnit.METRE));
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, positionBuilder::build);
		assertEquals("Buildable is invalid because of 1 reason (First reason: [Latitude of a position cannot be null!]) | See log for more details.", exception.getMessage());
	}

	@Test
	void testNullLongitudeBuild() {
		AkjonavPositionBuilder positionBuilder = new AkjonavPositionBuilder().withLatitude(1.0).withAltitude(new Length(new BigDecimal("3.0"), LengthUnit.METRE));
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, positionBuilder::build);
		assertEquals("Buildable is invalid because of 1 reason (First reason: [Longitude of a position cannot be null!]) | See log for more details.", exception.getMessage());
	}

	@Test
	void testAboveRangeLatitudeBuild() {
		AkjonavPositionBuilder positionBuilder = new AkjonavPositionBuilder().withLatitude(91.0).withLongitude(2.0).withAltitude(new Length(new BigDecimal("3.0"), LengthUnit.METRE));
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, positionBuilder::build);
		assertEquals("Buildable is invalid because of 1 reason (First reason: [Latitude of a position must be between -90 and 90!]) | See log for more details.", exception.getMessage());
	}

	@Test
	void testBelowRangeLatitudeBuild() {
		AkjonavPositionBuilder positionBuilder = new AkjonavPositionBuilder().withLatitude(-91.0).withLongitude(2.0).withAltitude(new Length(new BigDecimal("3.0"), LengthUnit.METRE));
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, positionBuilder::build);
		assertEquals("Buildable is invalid because of 1 reason (First reason: [Latitude of a position must be between -90 and 90!]) | See log for more details.", exception.getMessage());
	}

	@Test
	void testAboveRangeLongitudeBuild() {
		AkjonavPositionBuilder positionBuilder = new AkjonavPositionBuilder().withLatitude(1.0).withLongitude(181.0).withAltitude(new Length(new BigDecimal("3.0"), LengthUnit.METRE));
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, positionBuilder::build);
		assertEquals("Buildable is invalid because of 1 reason (First reason: [Longitude of a position must be between -180 and 180!]) | See log for more details.", exception.getMessage());
	}

	@Test
	void testBelowRangeLongitudeBuild() {
		AkjonavPositionBuilder positionBuilder = new AkjonavPositionBuilder().withLatitude(1.0).withLongitude(-181.0).withAltitude(new Length(new BigDecimal("3.0"), LengthUnit.METRE));
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, positionBuilder::build);
		assertEquals("Buildable is invalid because of 1 reason (First reason: [Longitude of a position must be between -180 and 180!]) | See log for more details.", exception.getMessage());
	}

	@Test
	void testValidDeserializationWithoutAltitude() {
		ObjectMapper objectMapper = jsonService.getObjectMapper();
		ObjectNode jsonPosition = objectMapper.createObjectNode();
		jsonPosition.put("type", "AkjonavPosition");
		ObjectNode jsonData = objectMapper.createObjectNode();
		jsonData.put("lat", 1.0);
		jsonData.put("lon", 2.0);
		jsonPosition.set("data", jsonData);

		AkjonavPosition position = new AkjonavPositionBuilder().deserialize(jsonPosition);
		assertEquals(1.0, position.getLatitude());
		assertEquals(2.0, position.getLongitude());
		assertNull(position.getAltitude());
	}

	@Test
	void testValidDeserializationWithAltitude() {
		ObjectMapper objectMapper = jsonService.getObjectMapper();
		ObjectNode jsonPosition = objectMapper.createObjectNode();
		jsonPosition.put("type", "AkjonavPosition");
		ObjectNode jsonData = objectMapper.createObjectNode();
		jsonData.put("lat", 1.0);
		jsonData.put("lon", 2.0);
		ObjectNode jsonAltitude = objectMapper.createObjectNode();
		jsonAltitude.put("value", 3.0);
		jsonAltitude.put("unit", "LengthUnit.METRE");
		jsonData.set("alt", jsonAltitude);
		jsonPosition.set("data", jsonData);

		AkjonavPosition position = new AkjonavPositionBuilder().deserialize(jsonPosition);
		assertEquals(1.0, position.getLatitude());
		assertEquals(2.0, position.getLongitude());
		assertNotNull(position.getAltitude());
		assertEquals(new BigDecimal("3.0"), position.getAltitude().getValue());
		assertEquals(LengthUnit.METRE, position.getAltitude().getUnit());
	}

	@Test
	void testValidTypeAfterDeserializationWithMissingType() {
		ObjectMapper objectMapper = jsonService.getObjectMapper();
		ObjectNode jsonPosition = objectMapper.createObjectNode();
		ObjectNode jsonData = objectMapper.createObjectNode();
		jsonData.put("lat", 1.0);
		jsonData.put("lon", 2.0);
		jsonPosition.set("data", jsonData);

		AkjonavPositionBuilder positionBuilder = new AkjonavPositionBuilder();
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> positionBuilder.deserialize(jsonPosition));
		assertEquals("Type ID of deserialized object is invalid or does not match type ID of builder!", exception.getMessage());
	}

	@Test
	void testValidTypeAfterDeserializationWithWrongType() {
		ObjectMapper objectMapper = jsonService.getObjectMapper();
		ObjectNode jsonPosition = objectMapper.createObjectNode();
		jsonPosition.put("type", "WrongType");
		ObjectNode jsonData = objectMapper.createObjectNode();
		jsonData.put("lat", 1.0);
		jsonData.put("lon", 2.0);
		jsonPosition.set("data", jsonData);

		AkjonavPositionBuilder positionBuilder = new AkjonavPositionBuilder();
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> positionBuilder.deserialize(jsonPosition));
		assertEquals("Type ID of deserialized object is invalid or does not match type ID of builder!", exception.getMessage());
	}

	@Test
	void testDeserializationWithMissingData() {
		ObjectMapper objectMapper = jsonService.getObjectMapper();
		ObjectNode jsonPosition = objectMapper.createObjectNode();
		jsonPosition.put("type", "AkjonavPosition");
		AkjonavPositionBuilder positionBuilder = new AkjonavPositionBuilder();
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> positionBuilder.deserialize(jsonPosition));
		assertEquals("Data of a buildable cannot be null!", exception.getMessage());
	}
}