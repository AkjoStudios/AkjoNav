package io.github.akjo03.akjonav.model.util.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
@Component
@SuppressWarnings("unused")
public abstract class AkjonavBuildable<T extends AkjonavBuildableType> {
	@NotNull protected final T type;

	public ObjectNode serialize(@NotNull ObjectMapper objectMapper) {
		ObjectNode objectNode = objectMapper.createObjectNode();

		objectNode = addRootProperties(objectNode, objectMapper);
		objectNode.put("type", type.getTypeID());

		ObjectNode dataNode = objectMapper.createObjectNode();
		objectNode.set("data", serializeIt(dataNode, objectMapper));

		return objectNode;
	}

	@SuppressWarnings("unused")
	protected ObjectNode addRootProperties(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) { return objectNode; }
	protected String getRootPropertiesString() { return ""; }
	protected abstract @NotNull ObjectNode serializeIt(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper);
	protected abstract @NotNull String toObjectString();

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{" + getRootPropertiesString() + ( !getRootPropertiesString().isBlank() ? ", " : "") + "type=" + type.getTypeID() + ", data=" + toObjectString() + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		AkjonavBuildable<?> that = (AkjonavBuildable<?>) o;
		return Objects.equals(type, that.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type);
	}
}