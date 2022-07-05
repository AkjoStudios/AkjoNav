package io.github.akjo03.akjonav.model.elements.base.node;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElement;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElementType;
import io.github.akjo03.akjonav.model.util.position.AkjonavPosition;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.Objects;

@SuppressWarnings("unused")
@Getter
public class AkjonavNode extends AkjonavBaseElement {
	@NotNull private final AkjonavPosition position;

	AkjonavNode(BigInteger elementID, @NotNull AkjonavPosition position) {
		super(elementID, AkjonavBaseElementType.NODE);
		this.position = position;
	}

	@Override
	protected @NotNull ObjectNode serializeElement(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) {
		objectNode.set("position", position.serialize(objectMapper));
		return objectNode;
	}

	@Override
	protected @NotNull String toObjectString() {
		return "{position=" + position + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		AkjonavNode that = (AkjonavNode) o;
		return position.equals(that.position);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), position);
	}
}