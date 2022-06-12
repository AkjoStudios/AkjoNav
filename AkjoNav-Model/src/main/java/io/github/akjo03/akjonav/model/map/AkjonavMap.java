package io.github.akjo03.akjonav.model.map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElement;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuildable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class AkjonavMap extends AkjonavBuildable<AkjonavMapType> {
	@NotNull private final List<AkjonavBaseElement> baseElements;

	public AkjonavMap(@NotNull List<AkjonavBaseElement> baseElements) {
		super(AkjonavMapType.type);
		this.baseElements = baseElements;
	}

	@Override
	protected @NotNull ObjectNode serializeIt(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) {
		ArrayNode baseElementsNode = objectMapper.createArrayNode();
		for (AkjonavBaseElement baseElement : baseElements) {
			baseElementsNode.add(baseElement.serialize(objectMapper));
		}
		objectNode.set("baseElements", baseElementsNode);

		return objectNode;
	}

	@Override
	protected @NotNull String toObjectString() {
		return "{baseElements=" + baseElements + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		AkjonavMap that = (AkjonavMap) o;
		return Objects.equals(baseElements, that.baseElements);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), baseElements);
	}
}