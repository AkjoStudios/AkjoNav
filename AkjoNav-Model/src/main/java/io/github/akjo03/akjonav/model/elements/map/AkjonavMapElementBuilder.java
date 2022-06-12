package io.github.akjo03.akjonav.model.elements.map;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.elements.AkjonavElementBuilder;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

@SuppressWarnings("unused")
public abstract class AkjonavMapElementBuilder<T extends AkjonavMapElement> extends AkjonavElementBuilder<AkjonavMapElementType, T> {
	protected AkjonavMapElementBuilder() { super(); }
	protected AkjonavMapElementBuilder(BigInteger elementID) { super(elementID); }

	public static AkjonavMapElement deserializeElement(@NotNull ObjectNode jsonObject) {
		return (AkjonavMapElement) AkjonavMapElementType.fromType(jsonObject.get("type").asText()).getBuilder().deserialize(jsonObject);
	}

	public static <T extends AkjonavMapElement> T deserializeElement(@NotNull ObjectNode jsonObject, @NotNull Class<T> elementClass) {
		try {
			return elementClass.cast(AkjonavMapElementType.fromType(jsonObject.get("type").asText()).getBuilder().deserialize(jsonObject));
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("Cannot deserialize element of type " + jsonObject.get("type").asText() + " to type " + elementClass.getSimpleName() + "!");
		}
	}
}