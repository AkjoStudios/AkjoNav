package io.github.akjo03.akjonav.model.map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuildable;
import org.jetbrains.annotations.NotNull;

public class AkjonavMap extends AkjonavBuildable {
	public AkjonavMap() {
		super(AkjonavMapType.type);
	}

	@Override
	protected @NotNull ObjectNode serializeIt(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) {
		return objectNode;
	}

	@Override
	protected @NotNull String toObjectString() {
		return "{}";
	}
}