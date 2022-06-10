package io.github.akjo03.akjonav.model.elements.base;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.elements.AkjonavElementBuilder;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

@SuppressWarnings("unused")
public abstract class AkjonavBaseElementBuilder<T extends AkjonavBaseElement> extends AkjonavElementBuilder<T> {
	protected AkjonavBaseElementBuilder() { super(); }
	protected AkjonavBaseElementBuilder(BigInteger elementID) { super(elementID); }

	public static AkjonavBaseElement deserializeElement(@NotNull ObjectNode jsonObject) {
		return (AkjonavBaseElement) AkjonavBaseElementType.fromType(jsonObject.get("type").asText()).getBuilder().deserialize(jsonObject);
	}

	public static <T extends AkjonavBaseElement> T deserializeElement(@NotNull ObjectNode jsonObject, @NotNull Class<T> elementClass) {
		try {
			return elementClass.cast(AkjonavBaseElementType.fromType(jsonObject.get("type").asText()).getBuilder().deserialize(jsonObject));
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("Cannot deserialize element of type " + jsonObject.get("type").asText() + " to type " + elementClass.getSimpleName() + "!");
		}
	}
}