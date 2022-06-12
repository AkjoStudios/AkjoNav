package io.github.akjo03.akjonav.model.elements.reference;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.elements.AkjonavElementType;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuildable;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

@Getter
@SuppressWarnings("unused")
public class AkjonavElementReference extends AkjonavBuildable<AkjonavElementReferenceType> {
	private final AkjonavElementType elementType;
	private final BigInteger elementID;

	protected AkjonavElementReference(AkjonavElementType elementType, BigInteger elementID) {
		super(AkjonavElementReferenceType.type);
		this.elementType = elementType;
		this.elementID = elementID;
	}

	@Override
	protected @NotNull ObjectNode serializeIt(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) {
		objectNode.put("elementType", elementType.getTypeID());
		objectNode.put("elementID", elementID.toString());
		return objectNode;
	}

	@Override
	protected @NotNull String toObjectString() {
		return "{elementType=" + elementType.getTypeID() + " elementID=" + elementID + "}";
	}

	@Override
	public String toString() {
		return "AkjonavElementReference" + "{" + "type=" + elementType + ", id=" + elementID + "}";
	}
}