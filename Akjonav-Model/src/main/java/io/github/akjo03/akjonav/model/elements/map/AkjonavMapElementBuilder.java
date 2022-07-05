package io.github.akjo03.akjonav.model.elements.map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.elements.AkjonavElementBuilder;
import io.github.akjo03.akjonav.model.elements.reference.AkjonavElementReference;
import io.github.akjo03.akjonav.model.elements.reference.AkjonavElementReferenceBuilder;
import io.validly.Notification;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static io.validly.NoteFirstValidator.valid;

@SuppressWarnings("unused")
public abstract class AkjonavMapElementBuilder<T extends AkjonavMapElement> extends AkjonavElementBuilder<AkjonavMapElementType, T> {
	private List<AkjonavElementReference> elementRefs = new ArrayList<>();

	protected AkjonavMapElementBuilder() { super(); }
	protected AkjonavMapElementBuilder(BigInteger elementID, List<AkjonavElementReference> elementRefs) {
		super(elementID);
		this.elementRefs = elementRefs;
	}

	public AkjonavMapElementBuilder<T> setElementRefs(List<AkjonavElementReference> elementRefs) {
		this.elementRefs = elementRefs;
		return this;
	}

	public AkjonavMapElementBuilder<T> addElementRef(AkjonavElementReference elementRef) {
		if (elementRef == null) {
			throw new IllegalArgumentException("Cannot add element reference to map element that is null!");
		}
		if (elementRefs.stream().anyMatch(er -> er.getElementID().equals(elementRef.getElementID()))) {
			throw new IllegalArgumentException("Cannot add element reference to map element that is already present!");
		}
		this.elementRefs.add(elementRef);
		return this;
	}

	@Override
	protected @NotNull Notification validateElement() {
		Notification notification = validateMapElement();

		valid(elementRefs, "AkjonavMapElement.elementRefs", notification)
				.mustNotBeNull("Element references of an AkjonavMapElement cannot be null!");

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
		ArrayNode elementRefsNode = (ArrayNode) objectNode.get("elementRefs");
		for (JsonNode elementRefNode : elementRefsNode) {
			elementRefs.add(new AkjonavElementReferenceBuilder().deserialize((ObjectNode) elementRefNode));
		}
		super.deserializeRootProperties(objectNode);
	}

	protected abstract @NotNull Notification validateMapElement();
}