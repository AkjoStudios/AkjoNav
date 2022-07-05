package io.github.akjo03.akjonav.model.elements.map.highway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElementType;
import io.github.akjo03.akjonav.model.elements.base.way.AkjonavWay;
import io.github.akjo03.akjonav.model.elements.map.AkjonavMapElement;
import io.github.akjo03.akjonav.model.elements.map.AkjonavMapElementType;
import io.github.akjo03.akjonav.model.elements.reference.AkjonavElementReference;
import io.github.akjo03.akjonav.model.map.AkjonavMapBuilder;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

public class AkjonavHighway extends AkjonavMapElement {
	protected AkjonavHighway(BigInteger elementID, List<AkjonavElementReference> elementRefs, AkjonavMapBuilder mapBuilderRef) {
		super(elementID, AkjonavMapElementType.HIGHWAY, elementRefs, mapBuilderRef);
	}

	public AkjonavElementReference getStartNodeRef() {
		AkjonavElementReference firstElementRef = elementRefs.get(0);
		if (firstElementRef.getElementType().equals(AkjonavBaseElementType.WAY)) {
			AkjonavWay firstWay = (AkjonavWay) mapBuilderRef.getBaseElementByReference(firstElementRef);
			return Objects.requireNonNull(firstWay).getStartNodeRef();
		} else if (firstElementRef.getElementType().equals(AkjonavMapElementType.HIGHWAY)) {
			AkjonavHighway firstHighway = (AkjonavHighway) mapBuilderRef.getMapElementByReference(firstElementRef);
			return Objects.requireNonNull(firstHighway).getStartNodeRef();
		} else {
			throw new IllegalStateException("First element reference is not a way or a highway");
		}
	}

	public AkjonavElementReference getEndNodeRef() {
		AkjonavElementReference lastElementRef = elementRefs.get(elementRefs.size() - 1);
		if (lastElementRef.getElementType().equals(AkjonavBaseElementType.WAY)) {
			AkjonavWay lastWay = (AkjonavWay) mapBuilderRef.getBaseElementByReference(lastElementRef);
			return Objects.requireNonNull(lastWay).getEndNodeRef();
		} else if (lastElementRef.getElementType().equals(AkjonavMapElementType.HIGHWAY)) {
			AkjonavHighway lastHighway = (AkjonavHighway) mapBuilderRef.getMapElementByReference(lastElementRef);
			return Objects.requireNonNull(lastHighway).getEndNodeRef();
		} else {
			throw new IllegalStateException("Last element reference is not a way or a highway");
		}
	}

	@Override
	protected @NotNull ObjectNode serializeElement(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) {
		return objectNode;
	}

	@Override
	protected @NotNull String toObjectString() {
		return "{}";
	}
}