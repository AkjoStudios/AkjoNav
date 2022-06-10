package io.github.akjo03.akjonav.model.map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuildableType;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuilder;
import io.validly.Notification;
import org.jetbrains.annotations.NotNull;

public class AkjonavMapBuilder extends AkjonavBuilder<AkjonavMap> {
	public AkjonavMapBuilder() { super(); }

	@Override
	protected AkjonavBuildableType getType() {
		return AkjonavMapType.type;
	}

	@Override
	protected AkjonavMap buildIt() {
		return new AkjonavMap();
	}

	@Override
	protected @NotNull Notification validateIt() {
		return new Notification();
	}

	@Override
	protected void fromSerialized(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) {
		// Doesn't have any serialized data yet
	}
}