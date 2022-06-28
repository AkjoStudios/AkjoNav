package io.github.akjo03.akjonav.model.map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElement;
import io.github.akjo03.akjonav.model.elements.base.node.AkjonavNode;
import io.github.akjo03.akjonav.model.elements.base.node.AkjonavNodeBuilder;
import io.github.akjo03.akjonav.model.elements.base.way.AkjonavWay;
import io.github.akjo03.akjonav.model.elements.base.way.AkjonavWayBuilder;
import io.github.akjo03.akjonav.model.elements.reference.AkjonavElementReference;
import io.github.akjo03.akjonav.model.services.JsonService;
import io.github.akjo03.akjonav.model.util.position.AkjonavPositionBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = { JsonService.class })
class AkjonavMapBuilderTest {
	private final JsonService jsonService;

	@Autowired
	protected AkjonavMapBuilderTest(JsonService jsonService) {
		this.jsonService = jsonService;
	}

	@Test
	void testEmptyConstructor() {
		AkjonavMapBuilder builder = new AkjonavMapBuilder();
		assertNotNull(builder.baseElements);
		assertEquals(0, builder.baseElements.size());
		assertNotNull(builder.mapElements);
		assertEquals(0, builder.mapElements.size());
		assertNotNull(builder.elementReferences);
		assertEquals(0, builder.elementReferences.size());
	}

	@Test
	void testCreationWithSingleNode() {
		AkjonavMapBuilder builder = new AkjonavMapBuilder();
		AkjonavNode node = new AkjonavNodeBuilder(BigInteger.valueOf(1))
				.setPosition(new AkjonavPositionBuilder(0.0, 0.0).build())
				.build();
		builder.addBaseElement(node);
		assertEquals(1, builder.baseElements.size());
		assertEquals(0, builder.mapElements.size());
		assertEquals(node,
				builder.baseElements.stream()
						.filter(e -> e.getElementID().equals(node.getElementID()))
						.findFirst()
						.orElse(null)
		);
		AkjonavElementReference reference = builder.getBaseElementReference(BigInteger.valueOf(1));
		assertEquals(1, builder.elementReferences.size());
		assertNotNull(reference);
		assertEquals(node.getElementID(), reference.getElementID());
		assertEquals(node.getType(), reference.getElementType());
	}

	@Test
	void testCreationWithMultipleNodes() {
		AkjonavMapBuilder builder = new AkjonavMapBuilder();
		AkjonavNode node1 = new AkjonavNodeBuilder(BigInteger.valueOf(1)).setPosition(new AkjonavPositionBuilder(0.0, 0.0).build()).build();
		AkjonavNode node2 = new AkjonavNodeBuilder(BigInteger.valueOf(2)).setPosition(new AkjonavPositionBuilder(1.0, 1.0).build()).build();
		builder.addBaseElement(node1);
		builder.addBaseElement(node2);
		assertEquals(2, builder.baseElements.size());
		assertEquals(0, builder.mapElements.size());
		assertEquals(node1, builder.baseElements.stream().filter(e -> e.getElementID().equals(node1.getElementID())).findFirst().orElse(null));
		assertEquals(node2, builder.baseElements.stream().filter(e -> e.getElementID().equals(node2.getElementID())).findFirst().orElse(null));
		AkjonavElementReference reference1 = builder.getBaseElementReference(BigInteger.valueOf(1));
		AkjonavElementReference reference2 = builder.getBaseElementReference(BigInteger.valueOf(2));
		assertEquals(2, builder.elementReferences.size());
		assertNotNull(reference1);
		assertEquals(node1.getElementID(), reference1.getElementID());
		assertEquals(node1.getType(), reference1.getElementType());
		assertNotNull(reference2);
		assertEquals(node2.getElementID(), reference2.getElementID());
		assertEquals(node2.getType(), reference2.getElementType());
	}

	@Test
	void testCreationWithSameNode() {
		AkjonavMapBuilder builder = new AkjonavMapBuilder();
		AkjonavNode node = new AkjonavNodeBuilder(BigInteger.valueOf(1)).setPosition(new AkjonavPositionBuilder(0.0, 0.0).build()).build();
		builder.addBaseElement(node);
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> builder.addBaseElement(node));
		assertEquals("Base element with ID 1 already exists!", exception.getMessage());
	}

	@Test
	void testCreationWithMultipleNodesAsList() {
		AkjonavMapBuilder builder = new AkjonavMapBuilder();
		AkjonavNode node1 = new AkjonavNodeBuilder(BigInteger.valueOf(1)).setPosition(new AkjonavPositionBuilder(0.0, 0.0).build()).build();
		AkjonavNode node2 = new AkjonavNodeBuilder(BigInteger.valueOf(2)).setPosition(new AkjonavPositionBuilder(1.0, 1.0).build()).build();
		builder.addBaseElements(List.of(node1, node2));
		assertEquals(2, builder.baseElements.size());
		assertEquals(0, builder.mapElements.size());
		assertEquals(node1, builder.baseElements.stream().filter(e -> e.getElementID().equals(node1.getElementID())).findFirst().orElse(null));
		assertEquals(node2, builder.baseElements.stream().filter(e -> e.getElementID().equals(node2.getElementID())).findFirst().orElse(null));
		AkjonavElementReference reference1 = builder.getBaseElementReference(BigInteger.valueOf(1));
		AkjonavElementReference reference2 = builder.getBaseElementReference(BigInteger.valueOf(2));
		assertEquals(2, builder.elementReferences.size());
		assertNotNull(reference1);
		assertEquals(node1.getElementID(), reference1.getElementID());
		assertEquals(node1.getType(), reference1.getElementType());
		assertNotNull(reference2);
		assertEquals(node2.getElementID(), reference2.getElementID());
		assertEquals(node2.getType(), reference2.getElementType());
	}

	@Test
	void testCreationWithMultipleNodesAsListWithDuplicates() {
		AkjonavMapBuilder builder = new AkjonavMapBuilder();
		AkjonavNode node1 = new AkjonavNodeBuilder(BigInteger.valueOf(1)).setPosition(new AkjonavPositionBuilder(0.0, 0.0).build()).build();
		AkjonavNode node2 = new AkjonavNodeBuilder(BigInteger.valueOf(2)).setPosition(new AkjonavPositionBuilder(1.0, 1.0).build()).build();
		AkjonavNode node3 = new AkjonavNodeBuilder(BigInteger.valueOf(2)).setPosition(new AkjonavPositionBuilder(1.0, 1.0).build()).build();
		List<AkjonavBaseElement> nodes = List.of(node1, node2, node3);
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> builder.addBaseElements(nodes));
		assertEquals("Base element with ID 2 already exists!", exception.getMessage());
	}

	// TODO: Add map element tests as soon as they are implemented

	@Test
	void testElementReferenceDeserialization() {
		AkjonavMapBuilder builder = new AkjonavMapBuilder();
		AkjonavNode node = new AkjonavNodeBuilder(BigInteger.valueOf(1)).setPosition(new AkjonavPositionBuilder(0.0, 0.0).build()).build();
		builder.addBaseElement(node);
		AkjonavElementReference reference = builder.getBaseElementReference(BigInteger.valueOf(1));
		ObjectNode objectNode = reference.serialize(jsonService.getObjectMapper());
		AkjonavElementReference deserializedReference = AkjonavMapBuilder.deserializeElementReference(objectNode);
		assertEquals(reference, deserializedReference);
	}

	@Test
	void testValidMapCreation() {
		AkjonavMapBuilder builder = new AkjonavMapBuilder();
		AkjonavNode node1 = new AkjonavNodeBuilder(BigInteger.valueOf(1)).setPosition(new AkjonavPositionBuilder(0.0, 0.0).build()).build();
		AkjonavNode node2 = new AkjonavNodeBuilder(BigInteger.valueOf(2)).setPosition(new AkjonavPositionBuilder(1.0, 1.0).build()).build();
		builder.addBaseElements(List.of(node1, node2));
		AkjonavWay way = new AkjonavWayBuilder(BigInteger.valueOf(3))
				.addNodes(List.of(
						builder.getBaseElementReference(BigInteger.valueOf(1)),
						builder.getBaseElementReference(BigInteger.valueOf(2))
				)).build();
		builder.addBaseElement(way);
		AkjonavMap map = builder.build();
		assertEquals(3, map.getBaseElementCount());
		assertEquals(node1, map.getBaseElementByID(BigInteger.valueOf(1)));
		assertEquals(node2, map.getBaseElementByID(BigInteger.valueOf(2)));
		assertEquals(way, map.getBaseElementByID(BigInteger.valueOf(3)));
	}

	@Test
	void testValidDeserialization() {
		AkjonavMapBuilder builder = new AkjonavMapBuilder();
		ObjectMapper objectMapper = jsonService.getObjectMapper();
		ObjectNode objectNode = objectMapper.createObjectNode();
		objectNode.put("type", "AkjonavMap");
		ObjectNode dataNode = objectMapper.createObjectNode();
		ArrayNode baseElementsNode = objectMapper.createArrayNode();
		AkjonavNode node1 = new AkjonavNodeBuilder(BigInteger.valueOf(1)).setPosition(new AkjonavPositionBuilder(0.0, 0.0).build()).build();
		AkjonavNode node2 = new AkjonavNodeBuilder(BigInteger.valueOf(2)).setPosition(new AkjonavPositionBuilder(1.0, 1.0).build()).build();
		builder.addBaseElements(List.of(node1, node2));
		AkjonavWay way = new AkjonavWayBuilder(BigInteger.valueOf(3))
				.addNodes(List.of(
						builder.getBaseElementReference(BigInteger.valueOf(1)),
						builder.getBaseElementReference(BigInteger.valueOf(2))
				)).build();
		builder.addBaseElement(way);
		baseElementsNode.add(node1.serialize(objectMapper));
		baseElementsNode.add(node2.serialize(objectMapper));
		baseElementsNode.add(way.serialize(objectMapper));
		dataNode.set("baseElements", baseElementsNode);
		ArrayNode mapElementsNode = objectMapper.createArrayNode();
		dataNode.set("mapElements", mapElementsNode);
		objectNode.set("data", dataNode);
		AkjonavMap map = new AkjonavMapBuilder().deserialize(objectNode);
		assertEquals(3, map.getBaseElementCount());
		assertEquals(node1, map.getBaseElementByID(BigInteger.valueOf(1)));
		assertEquals(node2, map.getBaseElementByID(BigInteger.valueOf(2)));
		assertEquals(way, map.getBaseElementByID(BigInteger.valueOf(3)));
	}

	@Test
	void testInvalidDeserializationWithNullBaseElements() {
		AkjonavMapBuilder builder = new AkjonavMapBuilder();
		ObjectMapper objectMapper = jsonService.getObjectMapper();
		ObjectNode objectNode = objectMapper.createObjectNode();
		objectNode.put("type", "AkjonavMap");
		ObjectNode dataNode = objectMapper.createObjectNode();
		dataNode.set("baseElements", null);
		ArrayNode mapElementsNode = objectMapper.createArrayNode();
		dataNode.set("mapElements", mapElementsNode);
		objectNode.set("data", dataNode);
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> builder.deserialize(objectNode));
		assertEquals("BaseElements of and AkjonavMap must be not null and an array!", exception.getMessage());
	}

	@Test
	void testInvalidDeserializationWithNullMapElements() {
		AkjonavMapBuilder builder = new AkjonavMapBuilder();
		ObjectMapper objectMapper = jsonService.getObjectMapper();
		ObjectNode objectNode = objectMapper.createObjectNode();
		objectNode.put("type", "AkjonavMap");
		ObjectNode dataNode = objectMapper.createObjectNode();
		ArrayNode baseElementsNode = objectMapper.createArrayNode();
		dataNode.set("baseElements", baseElementsNode);
		dataNode.set("mapElements", null);
		objectNode.set("data", dataNode);
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> builder.deserialize(objectNode));
		assertEquals("MapElements of and AkjonavMap must be not null and an array!", exception.getMessage());
	}

	@Test
	void testInvalidDeserializationWithNullBaseElement() {
		AkjonavMapBuilder builder = new AkjonavMapBuilder();
		ObjectMapper objectMapper = jsonService.getObjectMapper();
		ObjectNode objectNode = objectMapper.createObjectNode();
		objectNode.put("type", "AkjonavMap");
		ObjectNode dataNode = objectMapper.createObjectNode();
		ArrayNode baseElementsNode = objectMapper.createArrayNode();
		baseElementsNode.add((JsonNode) null);
		dataNode.set("baseElements", baseElementsNode);
		ArrayNode mapElementsNode = objectMapper.createArrayNode();
		dataNode.set("mapElements", mapElementsNode);
		objectNode.set("data", dataNode);
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> builder.deserialize(objectNode));
		assertEquals("Buildable is invalid because of 1 reason (First reason: [No BaseElements in an AkjonavMap should be null!]) | See log for more details.", exception.getMessage());
	}

	@Test
	void testInvalidDeserializationWithNullMapElement() {
		AkjonavMapBuilder builder = new AkjonavMapBuilder();
		ObjectMapper objectMapper = jsonService.getObjectMapper();
		ObjectNode objectNode = objectMapper.createObjectNode();
		objectNode.put("type", "AkjonavMap");
		ObjectNode dataNode = objectMapper.createObjectNode();
		ArrayNode baseElementsNode = objectMapper.createArrayNode();
		dataNode.set("baseElements", baseElementsNode);
		ArrayNode mapElementsNode = objectMapper.createArrayNode();
		mapElementsNode.add((JsonNode) null);
		dataNode.set("mapElements", mapElementsNode);
		objectNode.set("data", dataNode);
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> builder.deserialize(objectNode));
		assertEquals("Buildable is invalid because of 1 reason (First reason: [No MapElements in an AkjonavMap should be null!]) | See log for more details.", exception.getMessage());
	}

	@Test
	void testInvalidDeserializationWithDuplicateBaseElementIDs() {
		AkjonavMapBuilder builder = new AkjonavMapBuilder();
		ObjectMapper objectMapper = jsonService.getObjectMapper();
		ObjectNode objectNode = objectMapper.createObjectNode();
		objectNode.put("type", "AkjonavMap");
		ObjectNode dataNode = objectMapper.createObjectNode();
		ArrayNode baseElementsNode = objectMapper.createArrayNode();
		AkjonavNode node1 = new AkjonavNodeBuilder(BigInteger.valueOf(1)).setPosition(new AkjonavPositionBuilder(0.0, 0.0).build()).build();
		AkjonavNode node2 = new AkjonavNodeBuilder(BigInteger.valueOf(1)).setPosition(new AkjonavPositionBuilder(1.0, 1.0).build()).build();
		baseElementsNode.add(node1.serialize(objectMapper));
		baseElementsNode.add(node2.serialize(objectMapper));
		dataNode.set("baseElements", baseElementsNode);
		ArrayNode mapElementsNode = objectMapper.createArrayNode();
		dataNode.set("mapElements", mapElementsNode);
		objectNode.set("data", dataNode);
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> builder.deserialize(objectNode));
		assertEquals("Buildable is invalid because of 1 reason (First reason: [BaseElements of an AkjonavMap must each have unique IDs!]) | See log for more details.", exception.getMessage());
	}

	// TODO: Test invalid deserialization with duplicate map element IDs
}