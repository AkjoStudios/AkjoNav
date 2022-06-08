package io.github.akjo03.akjonav.model.util.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
@Component
public abstract class AkjonavBuildable {
	protected final BigInteger id;
	protected final AkjonavBuildableType type;

	public ObjectNode serialize(@NotNull ObjectMapper objectMapper) {
		ObjectNode objectNode = objectMapper.createObjectNode();

		objectNode.put("id", id.toString());
		objectNode.put("type", type.getTypeID());

		ObjectNode dataNode = objectMapper.createObjectNode();
		objectNode.set("data", serializeIt(dataNode, objectMapper));

		return objectNode;
	}

	protected abstract ObjectNode serializeIt(ObjectNode objectNode, ObjectMapper objectMapper);
	protected abstract String toObjectString();

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{" + "id=" + id + ", type=" + type.getTypeID() + ", data=" + toObjectString() + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		AkjonavBuildable that = (AkjonavBuildable) o;
		return Objects.equals(id, that.id) && Objects.equals(type, that.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, type);
	}
}