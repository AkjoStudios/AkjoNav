package io.github.akjo03.akjonav.model.elements.base.way;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElement;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElementType;
import io.github.akjo03.akjonav.model.elements.base.node.AkjonavNode;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class AkjonavWay extends AkjonavBaseElement {
	@NotNull private final List<AkjonavNode> nodes;

	AkjonavWay(BigInteger elementID, @NotNull List<AkjonavNode> nodes) {
		super(elementID, AkjonavBaseElementType.WAY);
		this.nodes = nodes;
	}

	@Override
	protected @NotNull ObjectNode serializeElement(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) {
		ArrayNode nodesArray = objectMapper.createArrayNode();
		for (AkjonavNode node : nodes) {
			nodesArray.add(node.serialize(objectMapper));
		}
		objectNode.set("nodes", nodesArray);
		return objectNode;
	}

	@Override
	protected @NotNull String toObjectString() {
		return "{nodes=" + nodes + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		AkjonavWay that = (AkjonavWay) o;
		return nodes.equals(that.nodes);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), nodes);
	}
}