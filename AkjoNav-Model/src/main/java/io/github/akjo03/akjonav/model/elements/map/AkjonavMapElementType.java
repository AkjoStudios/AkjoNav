package io.github.akjo03.akjonav.model.elements.map;

import io.github.akjo03.akjonav.model.elements.AkjonavElementType;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuilder;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum AkjonavMapElementType implements AkjonavElementType {
	;

	private final String typeID;
	private final AkjonavMapElementBuilder<?> builder;

	@Override
	public String getTypeID() {
		return typeID;
	}

	@Override
	public AkjonavBuilder<?, ?> getBuilder() {
		return builder;
	}

	public static AkjonavMapElementType fromType(String typeID) {
		return Arrays.stream(values())
				.filter(type -> type.getTypeID().equals(typeID))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("No map element type found for id \"" + typeID + "\"!"));
	}

	@Override
	public String toString() {
		return typeID;
	}
}