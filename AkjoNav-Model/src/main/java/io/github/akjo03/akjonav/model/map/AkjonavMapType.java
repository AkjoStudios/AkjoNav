package io.github.akjo03.akjonav.model.map;

import io.github.akjo03.akjonav.model.util.builder.AkjonavBuildableType;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuilder;

public class AkjonavMapType implements AkjonavBuildableType<AkjonavMap> {
	public static final AkjonavMapType type = new AkjonavMapType();

	private static final String TYPE_ID = "AkjonavMap";
	private static final AkjonavMapBuilder BUILDER = new AkjonavMapBuilder();

	private AkjonavMapType() {}

	@Override
	public String getTypeID() {
		return TYPE_ID;
	}

	@Override
	public AkjonavBuilder<?, ?> getBuilder() {
		return BUILDER;
	}

	@Override
	public Class<AkjonavMap> getTypeClass() {
		return AkjonavMap.class;
	}

	@Override
	public String toString() {
		return TYPE_ID;
	}
}