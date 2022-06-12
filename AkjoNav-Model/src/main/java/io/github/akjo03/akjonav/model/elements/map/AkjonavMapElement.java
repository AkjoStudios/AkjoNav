package io.github.akjo03.akjonav.model.elements.map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.elements.AkjonavElement;
import io.github.akjo03.akjonav.model.elements.AkjonavElementReference;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

@Getter
public abstract class AkjonavMapElement extends AkjonavElement<AkjonavMapElementType> {
	protected final AkjonavElementReference baseElement;

	protected AkjonavMapElement(BigInteger elementID, AkjonavMapElementType elementType, AkjonavElementReference baseElement) {
		super(elementID, elementType);
		this.baseElement = baseElement;
	}

	@Override
	protected ObjectNode addRootProperties(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) {
		ObjectNode rootProperties = super.addRootProperties(objectNode, objectMapper);
		rootProperties.put("baseElementRef", baseElement.getElementID());
		return rootProperties;
	}

	@Override
	protected String getRootPropertiesString() {
		return super.getRootPropertiesString() + ", baseElementRef=" + baseElement.getElementID();
	}
}