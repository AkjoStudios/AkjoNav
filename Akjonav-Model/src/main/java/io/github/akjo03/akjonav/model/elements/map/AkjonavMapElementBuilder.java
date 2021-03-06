package io.github.akjo03.akjonav.model.elements.map;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.elements.AkjonavElementBuilder;
import io.github.akjo03.akjonav.model.elements.reference.AkjonavElementReference;
import io.github.akjo03.akjonav.model.map.AkjonavMapBuilder;
import io.validly.Notification;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

import static io.validly.NoteFirstValidator.valid;

@SuppressWarnings("unused")
public abstract class AkjonavMapElementBuilder<T extends AkjonavMapElement> extends AkjonavElementBuilder<AkjonavMapElementType, T> {
	private AkjonavElementReference baseElementRef;

	protected AkjonavMapElementBuilder() { super(); }
	protected AkjonavMapElementBuilder(BigInteger elementID, AkjonavElementReference baseElementRef) {
		super(elementID);
		this.baseElementRef = baseElementRef;
	}

	public AkjonavMapElementBuilder<T> setBaseElementRef(AkjonavElementReference baseElementRef) {
		this.baseElementRef = baseElementRef;
		return this;
	}

	@Override
	protected @NotNull Notification validateElement() {
		Notification notification = validateMapElement();

		valid(baseElementRef, "AkjonavMapElement.baseElementRef", notification)
				.mustNotBeNull("Base element reference of an AkjonavMapElement cannot be null!")
				.must(baseElementRefP -> baseElementRefP.getElementType().getTypeID().split(":")[0].equals("BaseElement"), "Base element reference of an AkjonavMapElement must be a reference to a BaseElement!");

		return notification;
	}

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

	@Override
	protected void deserializeRootProperties(@NotNull ObjectNode objectNode) {
		this.baseElementRef = AkjonavMapBuilder.deserializeElementReference(objectNode);
		super.deserializeRootProperties(objectNode);
	}

	protected abstract @NotNull Notification validateMapElement();
}