package io.github.akjo03.akjonav.model.map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElement;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElementBuilder;
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

@SuppressWarnings("unused")
public class AkjonavMapBuilder extends AkjonavBuilder<AkjonavMapType, AkjonavMap> {
	private final List<AkjonavBaseElement> baseElements = new ArrayList<>();

	private final List<AkjonavElementReference> elementReferences = new ArrayList<>();

	public AkjonavMapBuilder() { super(); }

	public AkjonavMapBuilder addBaseElement(@NotNull AkjonavBaseElement baseElement) {
		this.baseElements.add(baseElement);
		return this;
	}

	public AkjonavMapBuilder addBaseElements(@NotNull List<AkjonavBaseElement> baseElements) {
		this.baseElements.addAll(baseElements);
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

	@Override
	protected AkjonavBuildableType getType() {
		return AkjonavMapType.type;
	}

	@Override
	protected AkjonavMap buildIt() {
		return new AkjonavMap(baseElements);
	}

	@Override
	protected @NotNull Notification validateIt() {
		Notification notification = new Notification();
		valid(baseElements, "AkjonavMap.baseElements", notification)
				.mustNotBeNull("BaseElements of an AkjonavMap cannot be null!");
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
	}
}