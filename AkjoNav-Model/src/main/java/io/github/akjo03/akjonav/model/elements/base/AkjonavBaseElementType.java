package io.github.akjo03.akjonav.model.elements.base;

import io.github.akjo03.akjonav.model.elements.AkjonavElementType;
import io.github.akjo03.akjonav.model.elements.base.node.AkjonavNodeBuilder;
import io.github.akjo03.akjonav.model.elements.base.way.AkjonavWayBuilder;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuilder;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum AkjonavBaseElementType implements AkjonavElementType {
	NODE("BaseElement:NODE", new AkjonavNodeBuilder()),
	WAY("BaseElement:WAY", new AkjonavWayBuilder());

	private final String typeID;
	private final AkjonavBaseElementBuilder<?> builder;

	@Override
	public String getTypeID() {
		return typeID;
	}

	@Override
	public AkjonavBuilder<?, ?> getBuilder() {
		return builder;
	}

	public static AkjonavBaseElementType fromType(String typeID) {
		return Arrays.stream(values())
				.filter(type -> type.getTypeID().equals(typeID))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("No base element type found for id \"" + typeID + "\"!"));
	}
}