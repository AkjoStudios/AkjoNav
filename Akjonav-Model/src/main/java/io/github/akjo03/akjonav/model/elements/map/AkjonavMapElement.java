package io.github.akjo03.akjonav.model.elements.map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.elements.AkjonavElement;
import io.github.akjo03.akjonav.model.elements.reference.AkjonavElementReference;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

@Getter
public abstract class AkjonavMapElement extends AkjonavElement<AkjonavMapElementType> {
	private final AkjonavElementReference baseElementRef;

	protected AkjonavMapElement(BigInteger elementID, AkjonavMapElementType elementType, AkjonavElementReference baseElementRef) {
		super(elementID, elementType);
		this.baseElementRef = baseElementRef;
	}

	@Override
	protected ObjectNode addRootProperties(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) {
		objectNode.put("baseElementRef", baseElementRef.getElementID());
		return super.addRootProperties(objectNode, objectMapper);
	}

	@Override
	protected String getRootPropertiesString() {
		return super.getRootPropertiesString() + ", baseElementRef=" + baseElementRef.getElementID();
	}
}