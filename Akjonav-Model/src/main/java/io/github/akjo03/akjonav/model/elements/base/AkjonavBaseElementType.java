package io.github.akjo03.akjonav.model.elements.base;

import io.github.akjo03.akjonav.model.elements.AkjonavElementType;
import io.github.akjo03.akjonav.model.elements.base.area.AkjonavArea;
import io.github.akjo03.akjonav.model.elements.base.area.AkjonavAreaBuilder;
import io.github.akjo03.akjonav.model.elements.base.node.AkjonavNode;
import io.github.akjo03.akjonav.model.elements.base.node.AkjonavNodeBuilder;
import io.github.akjo03.akjonav.model.elements.base.way.AkjonavWay;
import io.github.akjo03.akjonav.model.elements.base.way.AkjonavWayBuilder;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuilder;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum AkjonavBaseElementType implements AkjonavElementType<AkjonavBaseElement> {
	NODE("BaseElement:NODE", new AkjonavNodeBuilder(), AkjonavNode.class),
	WAY("BaseElement:WAY", new AkjonavWayBuilder(), AkjonavWay.class),
	AREA("BaseElement:AREA", new AkjonavAreaBuilder(), AkjonavArea.class);

	private final String typeID;
	private final AkjonavBaseElementBuilder<?> builder;
	private final Class<? extends AkjonavBaseElement> typeClass;

	@Override
	public String getTypeID() {
		return typeID;
	}

	@Override
	public AkjonavBuilder<?, ?> getBuilder() {
		return builder;
	}

	@Override
	public Class<? extends AkjonavBaseElement> getTypeClass() {
		return typeClass;
	}

	public static AkjonavBaseElementType fromType(String typeID) {
		return Arrays.stream(values())
				.filter(type -> type.getTypeID().equals(typeID))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("No base element type found for id \"" + typeID + "\"!"));
	}

	@Override
	public String toString() {
		return typeID;
	}
}