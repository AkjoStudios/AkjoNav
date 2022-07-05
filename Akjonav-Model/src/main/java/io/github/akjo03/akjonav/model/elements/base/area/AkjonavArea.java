package io.github.akjo03.akjonav.model.elements.base.area;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElement;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElementType;
import io.github.akjo03.akjonav.model.elements.reference.AkjonavElementReference;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
@Getter
public class AkjonavArea extends AkjonavBaseElement {
	@NotNull
	private final List<AkjonavElementReference> nodeRefs;

	protected AkjonavArea(BigInteger elementID, @NotNull List<AkjonavElementReference> nodeRefs) {
		super(elementID, AkjonavBaseElementType.AREA);
		this.nodeRefs = nodeRefs;
	}

	@Override
	protected @NotNull ObjectNode serializeElement(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) {
		ArrayNode nodesArray = objectMapper.createArrayNode();
		for (AkjonavElementReference node : nodeRefs) {
			nodesArray.add(node.serialize(objectMapper));
		}
		objectNode.set("nodeRefs", nodesArray);
		return objectNode;
	}

	@Override
	protected @NotNull String toObjectString() {
		return "{nodeRefs=" + nodeRefs + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		AkjonavArea that = (AkjonavArea) o;
		return nodeRefs.equals(that.nodeRefs);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), nodeRefs);
	}
}