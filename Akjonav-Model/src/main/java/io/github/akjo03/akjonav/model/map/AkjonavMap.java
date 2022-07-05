package io.github.akjo03.akjonav.model.map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElement;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElementType;
import io.github.akjo03.akjonav.model.elements.map.AkjonavMapElement;
import io.github.akjo03.akjonav.model.elements.map.AkjonavMapElementType;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuildable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class AkjonavMap extends AkjonavBuildable<AkjonavMapType> {
	@NotNull private final List<AkjonavBaseElement> baseElements;
	@NotNull private final List<AkjonavMapElement> mapElements;

	public AkjonavMap(@NotNull List<AkjonavBaseElement> baseElements, @NotNull List<AkjonavMapElement> mapElements) {
		super(AkjonavMapType.type);
		this.baseElements = baseElements;
		this.mapElements = mapElements;
	}

	public @Nullable AkjonavBaseElement getBaseElementByID(@NotNull BigInteger id) {
		return baseElements.stream()
				.filter(baseElementP -> baseElementP.getElementID().equals(id))
				.findFirst()
				.orElse(null);
	}

	public @Nullable AkjonavMapElement getMapElementByID(@NotNull BigInteger id) {
		return mapElements.stream()
				.filter(mapElementP -> mapElementP.getElementID().equals(id))
				.findFirst()
				.orElse(null);
	}

	public @NotNull List<? extends AkjonavBaseElement> getBaseElementsByID(@NotNull List<BigInteger> ids) {
		return baseElements.stream()
				.filter(baseElementP -> ids.contains(baseElementP.getElementID()))
				.toList();
	}

	public @NotNull List< ? extends AkjonavMapElement> getMapElementsByID(@NotNull List<BigInteger> ids) {
		return mapElements.stream()
				.filter(mapElementP -> ids.contains(mapElementP.getElementID()))
				.toList();
	}

	public @NotNull List<? extends AkjonavBaseElement> getBaseElementsByType(@NotNull AkjonavBaseElementType type) {
		return baseElements.stream()
				.filter(baseElementP -> baseElementP.getType().equals(type))
				.map(baseElementP -> type.getTypeClass().cast(baseElementP))
				.toList();
	}

	public @NotNull List<? extends AkjonavMapElement> getMapElementsByType(@NotNull AkjonavMapElementType type) {
		return mapElements.stream()
				.filter(mapElementP -> mapElementP.getType().equals(type))
				.map(mapElementP -> type.getTypeClass().cast(mapElementP))
				.toList();
	}

	public @NotNull List<? extends AkjonavBaseElement> getBaseElementsByTypes(@NotNull List<AkjonavBaseElementType> types) {
		return baseElements.stream()
				.filter(baseElementP -> types.contains(baseElementP.getType()))
				.map(baseElementP -> types.get(types.indexOf(baseElementP.getType())).getTypeClass().cast(baseElementP))
				.toList();

	}

	public @NotNull List<? extends AkjonavMapElement> getMapElementsByTypes(@NotNull List<AkjonavMapElementType> types) {
		return mapElements.stream()
				.filter(mapElementP -> types.contains(mapElementP.getType()))
				.map(mapElementP -> types.get(types.indexOf(mapElementP.getType())).getTypeClass().cast(mapElementP))
				.toList();
	}

	public void forEachBaseElement(@NotNull Consumer<AkjonavBaseElement> consumer) {
		baseElements.forEach(consumer);
	}

	public void forEachMapElement(@NotNull Consumer<AkjonavMapElement> consumer) {
		mapElements.forEach(consumer);
	}

	public int getBaseElementCount() {
		return baseElements.size();
	}

	public int getMapElementCount() {
		return mapElements.size();
	}

	@Override
	protected @NotNull ObjectNode serializeIt(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) {
		ArrayNode baseElementsNode = objectMapper.createArrayNode();
		for (AkjonavBaseElement baseElement : baseElements) {
			baseElementsNode.add(baseElement.serialize(objectMapper));
		}
		objectNode.set("baseElements", baseElementsNode);

		ArrayNode mapElementsNode = objectMapper.createArrayNode();
		for (AkjonavMapElement mapElement : mapElements) {
			mapElementsNode.add(mapElement.serialize(objectMapper));
		}
		objectNode.set("mapElements", mapElementsNode);

		return objectNode;
	}

	@Override
	protected @NotNull String toObjectString() {
		return "{baseElements=" + baseElements + ", mapElements=" + mapElements + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		AkjonavMap that = (AkjonavMap) o;
		return Objects.equals(baseElements, that.baseElements) &&
				Objects.equals(mapElements, that.mapElements);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), baseElements, mapElements);
	}
}