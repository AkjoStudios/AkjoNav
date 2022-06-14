package io.github.akjo03.akjonav.model.map;

import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElement;
import io.github.akjo03.akjonav.model.elements.base.node.AkjonavNode;
import io.github.akjo03.akjonav.model.elements.base.node.AkjonavNodeBuilder;
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

	@Test
	void testCreationWithSingleNode() {
		AkjonavMapBuilder builder = new AkjonavMapBuilder();
		AkjonavNode node = new AkjonavNodeBuilder(BigInteger.valueOf(1))
				.setPosition(new AkjonavPositionBuilder(0.0, 0.0).build())
				.build();
		builder.addBaseElement(node);
		assertEquals(1, builder.getBaseElements().size());
		assertEquals(0, builder.getMapElements().size());
		assertEquals(node,
				builder.getBaseElements().stream()
						.filter(e -> e.getElementID().equals(node.getElementID()))
						.findFirst()
						.orElse(null)
		);
		AkjonavElementReference reference = builder.getBaseElementReference(BigInteger.valueOf(1));
		assertEquals(1, builder.getElementReferences().size());
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
		assertEquals(2, builder.getBaseElements().size());
		assertEquals(0, builder.getMapElements().size());
		assertEquals(node1, builder.getBaseElements().stream().filter(e -> e.getElementID().equals(node1.getElementID())).findFirst().orElse(null));
		assertEquals(node2, builder.getBaseElements().stream().filter(e -> e.getElementID().equals(node2.getElementID())).findFirst().orElse(null));
		AkjonavElementReference reference1 = builder.getBaseElementReference(BigInteger.valueOf(1));
		AkjonavElementReference reference2 = builder.getBaseElementReference(BigInteger.valueOf(2));
		assertEquals(2, builder.getElementReferences().size());
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
		assertEquals(2, builder.getBaseElements().size());
		assertEquals(0, builder.getMapElements().size());
		assertEquals(node1, builder.getBaseElements().stream().filter(e -> e.getElementID().equals(node1.getElementID())).findFirst().orElse(null));
		assertEquals(node2, builder.getBaseElements().stream().filter(e -> e.getElementID().equals(node2.getElementID())).findFirst().orElse(null));
		AkjonavElementReference reference1 = builder.getBaseElementReference(BigInteger.valueOf(1));
		AkjonavElementReference reference2 = builder.getBaseElementReference(BigInteger.valueOf(2));
		assertEquals(2, builder.getElementReferences().size());
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
}