package io.github.akjo03.akjonav.model.elements;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuildable;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.Objects;

@Getter
public abstract class AkjonavElement<T extends AkjonavElementType<?>> extends AkjonavBuildable<T> {
	private final BigInteger elementID;

	protected AkjonavElement(BigInteger elementID, T elementType) {
		super(elementType);
		this.elementID = elementID;
	}

	@Override
	protected @NotNull ObjectNode serializeIt(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) {
		return serializeElement(objectNode, objectMapper);
	}

	@Override
	protected ObjectNode addRootProperties(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) {
		objectNode.put("id", elementID);
		return objectNode;
	}

	@Override
	protected String getRootPropertiesString() {
		return "elementID=" + elementID;
	}

	protected abstract @NotNull ObjectNode serializeElement(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper);

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		AkjonavElement<?> that = (AkjonavElement<?>) o;
		return Objects.equals(elementID, that.elementID);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), elementID);
	}
}