package io.github.akjo03.akjonav.model.map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElement;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElementType;
import io.github.akjo03.akjonav.model.elements.base.area.AkjonavArea;
import io.github.akjo03.akjonav.model.elements.base.node.AkjonavNode;
import io.github.akjo03.akjonav.model.elements.base.node.AkjonavNodeBuilder;
import io.github.akjo03.akjonav.model.elements.base.way.AkjonavWay;
import io.github.akjo03.akjonav.model.elements.base.way.AkjonavWayBuilder;
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
class AkjonavMapTest {
	private static final AkjonavMapBuilder MAP_BUILDER = new AkjonavMapBuilder();
	private static final AkjonavMap TEST_MAP = MAP_BUILDER
			.addBaseElements(List.of(
					new AkjonavNodeBuilder(BigInteger.valueOf(1))
							.setPosition(new AkjonavPositionBuilder(0.0, 0.0).build())
							.build(),
					new AkjonavNodeBuilder(BigInteger.valueOf(2))
							.setPosition(new AkjonavPositionBuilder(1.0, 0.0).build())
							.build(),
					new AkjonavNodeBuilder(BigInteger.valueOf(3))
							.setPosition(new AkjonavPositionBuilder(1.0, 1.0).build())
							.build()
			)).addBaseElement(
					new AkjonavWayBuilder(BigInteger.valueOf(4))
							.addNodes(List.of(
									MAP_BUILDER.getBaseElementReference(BigInteger.valueOf(1)),
									MAP_BUILDER.getBaseElementReference(BigInteger.valueOf(2)),
									MAP_BUILDER.getBaseElementReference(BigInteger.valueOf(3))
							)).build()
			).build();

	private final JsonService jsonService;

	@Autowired
	protected AkjonavMapTest(JsonService jsonService) {
		this.jsonService = jsonService;
	}

	@Test
	void testGetExistingBaseElementByID() {
		AkjonavBaseElement baseElement = TEST_MAP.getBaseElementByID(BigInteger.valueOf(1));
		assertNotNull(baseElement);
		assertEquals(BigInteger.valueOf(1), baseElement.getElementID());
		assertEquals(AkjonavBaseElementType.NODE, baseElement.getType());
		AkjonavNode node = assertDoesNotThrow(() -> (AkjonavNode) baseElement);
		assertEquals(new AkjonavPositionBuilder(0.0, 0.0).build(), node.getPosition());
	}

	@Test
	void testGetNonExistingBaseElementByID() {
		AkjonavBaseElement baseElement = TEST_MAP.getBaseElementByID(BigInteger.valueOf(5));
		assertNull(baseElement);
	}

	@Test
	void testGetAllExistingElementsByID() {
		List<? extends AkjonavBaseElement> baseElements = TEST_MAP.getBaseElementsByID(List.of(
				BigInteger.valueOf(1),
				BigInteger.valueOf(2),
				BigInteger.valueOf(3),
				BigInteger.valueOf(4)
		));
		assertEquals(4, baseElements.size());
		assertEquals(BigInteger.valueOf(1), baseElements.get(0).getElementID());
		assertEquals(BigInteger.valueOf(2), baseElements.get(1).getElementID());
		assertEquals(BigInteger.valueOf(3), baseElements.get(2).getElementID());
		assertEquals(BigInteger.valueOf(4), baseElements.get(3).getElementID());

		assertEquals(AkjonavBaseElementType.NODE, baseElements.get(0).getType());
		assertEquals(AkjonavBaseElementType.NODE, baseElements.get(1).getType());
		assertEquals(AkjonavBaseElementType.NODE, baseElements.get(2).getType());
		assertEquals(AkjonavBaseElementType.WAY, baseElements.get(3).getType());

		AkjonavNode node1 = assertDoesNotThrow(() -> (AkjonavNode) baseElements.get(0));
		assertEquals(new AkjonavPositionBuilder(0.0, 0.0).build(), node1.getPosition());
		AkjonavNode node2 = assertDoesNotThrow(() -> (AkjonavNode) baseElements.get(1));
		assertEquals(new AkjonavPositionBuilder(1.0, 0.0).build(), node2.getPosition());
		AkjonavNode node3 = assertDoesNotThrow(() -> (AkjonavNode) baseElements.get(2));
		assertEquals(new AkjonavPositionBuilder(1.0, 1.0).build(), node3.getPosition());
		AkjonavWay way = assertDoesNotThrow(() -> (AkjonavWay) baseElements.get(3));
		assertEquals(List.of(
				MAP_BUILDER.getBaseElementReference(BigInteger.valueOf(1)),
				MAP_BUILDER.getBaseElementReference(BigInteger.valueOf(2)),
				MAP_BUILDER.getBaseElementReference(BigInteger.valueOf(3))
		), way.getNodeRefs());
	}

	@Test
	void testGetSomeNonExistingElementsByID() {
		List<? extends AkjonavBaseElement> baseElements = TEST_MAP.getBaseElementsByID(List.of(BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(5)));
		assertEquals(2, baseElements.size());
		assertEquals(BigInteger.valueOf(1), baseElements.get(0).getElementID());
		assertEquals(BigInteger.valueOf(2), baseElements.get(1).getElementID());

		assertEquals(AkjonavBaseElementType.NODE, baseElements.get(0).getType());
		assertEquals(AkjonavBaseElementType.NODE, baseElements.get(1).getType());

		AkjonavNode node1 = assertDoesNotThrow(() -> (AkjonavNode) baseElements.get(0));
		assertEquals(new AkjonavPositionBuilder(0.0, 0.0).build(), node1.getPosition());
		AkjonavNode node2 = assertDoesNotThrow(() -> (AkjonavNode) baseElements.get(1));
		assertEquals(new AkjonavPositionBuilder(1.0, 0.0).build(), node2.getPosition());
	}

	@Test
	void testGetAllNonExistingElementsByID() {
		List<? extends AkjonavBaseElement> baseElements = TEST_MAP.getBaseElementsByID(List.of(BigInteger.valueOf(5), BigInteger.valueOf(6)));
		assertEquals(0, baseElements.size());
	}

	@Test
	void testGetAllNodes() {
		List<AkjonavNode> nodes = TEST_MAP.getBaseElementsByType(AkjonavBaseElementType.NODE)
				.stream().map(AkjonavNode.class::cast).toList();
		assertEquals(3, nodes.size());
		assertEquals(new AkjonavPositionBuilder(0.0, 0.0).build(), nodes.get(0).getPosition());
		assertEquals(new AkjonavPositionBuilder(1.0, 0.0).build(), nodes.get(1).getPosition());
		assertEquals(new AkjonavPositionBuilder(1.0, 1.0).build(), nodes.get(2).getPosition());
	}

	@Test
	void testGetAllWays() {
		List<AkjonavWay> ways = TEST_MAP.getBaseElementsByType(AkjonavBaseElementType.WAY)
				.stream().map(AkjonavWay.class::cast).toList();
		assertEquals(1, ways.size());
		assertEquals(List.of(
				MAP_BUILDER.getBaseElementReference(BigInteger.valueOf(1)),
				MAP_BUILDER.getBaseElementReference(BigInteger.valueOf(2)),
				MAP_BUILDER.getBaseElementReference(BigInteger.valueOf(3))
		), ways.get(0).getNodeRefs());
	}

	@Test
	void testGetAllAreas() {
		List<AkjonavArea> areas = TEST_MAP.getBaseElementsByType(AkjonavBaseElementType.AREA)
				.stream().map(AkjonavArea.class::cast).toList();
		assertEquals(0, areas.size());
	}

	@Test
	void testGetAllBaseElements() {
		List<? extends AkjonavBaseElement> baseElements = TEST_MAP.getBaseElementsByTypes(List.of(
				AkjonavBaseElementType.NODE,
				AkjonavBaseElementType.WAY,
				AkjonavBaseElementType.AREA
		));
		assertEquals(4, baseElements.size());
		assertEquals(AkjonavBaseElementType.NODE, baseElements.get(0).getType());
		assertEquals(AkjonavBaseElementType.NODE, baseElements.get(1).getType());
		assertEquals(AkjonavBaseElementType.NODE, baseElements.get(2).getType());
		assertEquals(AkjonavBaseElementType.WAY, baseElements.get(3).getType());
	}

	@Test
	void testGetBaseElementCount() {
		assertEquals(4, TEST_MAP.getBaseElementCount());
	}

	@Test
	void testValidSerialization() {
		JsonNode jsonNode = assertDoesNotThrow(() -> TEST_MAP.serialize(jsonService.getObjectMapper()));
		assertEquals("AkjonavMap", jsonNode.get("type").asText());
		JsonNode dataNode = jsonNode.get("data");
		ArrayNode baseElementsNode = (ArrayNode) dataNode.get("baseElements");
		assertEquals(4, baseElementsNode.size());
		assertEquals(AkjonavBaseElementType.NODE.getTypeID(), baseElementsNode.get(0).get("type").asText());
		assertEquals(AkjonavBaseElementType.NODE.getTypeID(), baseElementsNode.get(1).get("type").asText());
		assertEquals(AkjonavBaseElementType.NODE.getTypeID(), baseElementsNode.get(2).get("type").asText());
		assertEquals(AkjonavBaseElementType.WAY.getTypeID(), baseElementsNode.get(3).get("type").asText());
	}

	@Test
	void testToString() {
		List<? extends AkjonavBaseElement> baseElements = TEST_MAP.getBaseElementsByTypes(List.of(
				AkjonavBaseElementType.NODE,
				AkjonavBaseElementType.WAY,
				AkjonavBaseElementType.AREA
		));

		assertEquals("AkjonavMap{type=AkjonavMap, data={baseElements=" + baseElements + ", mapElements=[]}}", TEST_MAP.toString());
	}

	@Test
	void testEquals() {
		assertEquals(TEST_MAP, MAP_BUILDER.build());
	}
}