package io.github.akjo03.akjonav.model.elements.base.node;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElement;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElementType;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

public class AkjonavNode extends AkjonavBaseElement {
	protected AkjonavNode(BigInteger elementID) {
		super(elementID, AkjonavBaseElementType.NODE);
	}

	@Override
	protected @NotNull ObjectNode serializeElement(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) {
		return objectNode;
	}

	@Override
	protected @NotNull String toObjectString() {
		return "{}";
	}
}