package io.github.akjo03.akjonav.model.elements.map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.elements.AkjonavElement;
import io.github.akjo03.akjonav.model.elements.reference.AkjonavElementReference;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.List;

@Getter
public abstract class AkjonavMapElement extends AkjonavElement<AkjonavMapElementType> {
	private final List<AkjonavElementReference> elementRefs;

	protected AkjonavMapElement(BigInteger elementID, AkjonavMapElementType elementType, List<AkjonavElementReference> elementRefs) {
		super(elementID, elementType);
		this.elementRefs = elementRefs;
	}

	@Override
	protected ObjectNode addRootProperties(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) {
		ArrayNode elementRefsNode = objectMapper.createArrayNode();
		for (AkjonavElementReference elementRef : elementRefs) {
			elementRefsNode.add(elementRef.serialize(objectMapper));
		}
		objectNode.set("elementRefs", elementRefsNode);
		return super.addRootProperties(objectNode, objectMapper);
	}

	@Override
	protected String getRootPropertiesString() {
		return super.getRootPropertiesString() + ", elementRefs=" + elementRefs.stream().map(AkjonavElementReference::getElementID).toList();
	}
}