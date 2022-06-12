package io.github.akjo03.akjonav.model.map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElement;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElementBuilder;
import io.github.akjo03.akjonav.model.elements.map.AkjonavMapElement;
import io.github.akjo03.akjonav.model.elements.map.AkjonavMapElementBuilder;
import io.github.akjo03.akjonav.model.elements.reference.AkjonavElementReference;
import io.github.akjo03.akjonav.model.elements.reference.AkjonavElementReferenceBuilder;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuildableType;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuilder;
import io.validly.Notification;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static io.validly.NoteFirstValidator.valid;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class AkjonavMapBuilder extends AkjonavBuilder<AkjonavMapType, AkjonavMap> {
	private final List<AkjonavBaseElement> baseElements = new ArrayList<>();
	private final List<AkjonavMapElement> mapElements = new ArrayList<>();

	private final List<AkjonavElementReference> elementReferences = new ArrayList<>();

	public AkjonavMapBuilder() { super(); }

	public AkjonavMapBuilder addBaseElement(@NotNull AkjonavBaseElement baseElement) {
		if (baseElements.stream().anyMatch(element -> element.getElementID().equals(baseElement.getElementID()))) {
			throw new IllegalArgumentException("Base element with ID " + baseElement.getElementID() + " already exists!");
		}
		this.baseElements.add(baseElement);
		return this;
	}

	public AkjonavMapBuilder addBaseElements(@NotNull List<AkjonavBaseElement> newBaseElements) {
		newBaseElements.forEach(this::addBaseElement);
		return this;
	}

	public AkjonavMapBuilder addMapElement(@NotNull AkjonavMapElement mapElement) {
		if (mapElements.stream().anyMatch(element -> element.getElementID().equals(mapElement.getElementID()))) {
			throw new IllegalArgumentException("Map element with ID " + mapElement.getElementID() + " already exists!");
		}
		this.mapElements.add(mapElement);
		return this;
	}

	public AkjonavMapBuilder addMapElements(@NotNull List<AkjonavMapElement> mapElements) {
		mapElements.forEach(this::addMapElement);
		return this;
	}

	public AkjonavElementReference getBaseElementReference(@NotNull BigInteger id) {
		AkjonavElementReference reference = elementReferences.stream()
				.filter(elementReference -> elementReference.getElementID().equals(id))
				.findFirst()
				.orElse(null);

		if (reference == null) {
			AkjonavBaseElement baseElement = baseElements.stream()
					.filter(element -> element.getElementID().equals(id))
					.findFirst()
					.orElse(null);

			if (baseElement != null) {
				reference = new AkjonavElementReferenceBuilder(baseElement.getType(), baseElement.getElementID()).build();
				elementReferences.add(reference);
			}
		}

		return reference;
	}

	public AkjonavElementReference getMapElementReference(@NotNull BigInteger id) {
		AkjonavElementReference reference = elementReferences.stream()
				.filter(elementReference -> elementReference.getElementID().equals(id))
				.findFirst()
				.orElse(null);

		if (reference == null) {
			AkjonavMapElement mapElement = mapElements.stream()
					.filter(element -> element.getElementID().equals(id))
					.findFirst()
					.orElse(null);

			if (mapElement != null) {
				reference = new AkjonavElementReferenceBuilder(mapElement.getType(), mapElement.getElementID()).build();
				elementReferences.add(reference);
			}
		}

		return reference;
	}

	public static AkjonavElementReference deserializeElementReference(@NotNull ObjectNode objectNode) {
		return new AkjonavElementReferenceBuilder().deserialize(objectNode);
	}

	@Override
	protected AkjonavBuildableType getType() {
		return AkjonavMapType.type;
	}

	@Override
	protected AkjonavMap buildIt() {
		return new AkjonavMap(baseElements, mapElements);
	}

	@Override
	protected @NotNull Notification validateIt() {
		Notification notification = new Notification();
		valid(baseElements, "AkjonavMap.baseElements", notification)
				.mustNotBeNull("BaseElements of an AkjonavMap cannot be null!")
				.must(baseElementsP -> baseElementsP.stream().map(AkjonavBaseElement::getElementID).distinct().count() == baseElementsP.size(), "BaseElements of an AkjonavMap must each have unique IDs!");
		valid(mapElements, "AkjonavMap.mapElements", notification)
				.mustNotBeNull("MapElements of an AkjonavMap cannot be null!")
				.must(mapElementsP -> mapElementsP.stream().map(AkjonavMapElement::getElementID).distinct().count() == mapElementsP.size(), "MapElements of an AkjonavMap must each have unique IDs!");
		return notification;
	}

	@Override
	protected void fromSerialized(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) {
		ArrayNode baseElementsArray = (ArrayNode) objectNode.get("baseElements");
		if (baseElementsArray != null) {
			for (JsonNode baseElementNode : baseElementsArray) {
				this.baseElements.add(AkjonavBaseElementBuilder.deserializeElement((ObjectNode) baseElementNode));
			}
		}

		ArrayNode mapElementsArray = (ArrayNode) objectNode.get("mapElements");
		if (mapElementsArray != null) {
			for (JsonNode mapElementNode : mapElementsArray) {
				this.mapElements.add(AkjonavMapElementBuilder.deserializeElement((ObjectNode) mapElementNode));
			}
		}
	}
}