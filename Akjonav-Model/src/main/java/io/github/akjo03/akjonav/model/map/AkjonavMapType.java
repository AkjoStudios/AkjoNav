package io.github.akjo03.akjonav.model.map;

import io.github.akjo03.akjonav.model.util.builder.AkjonavBuildableType;

public class AkjonavMapType implements AkjonavBuildableType<AkjonavMapType, AkjonavMap, AkjonavMapBuilder> {
	public static final AkjonavMapType TYPE = new AkjonavMapType();

	private AkjonavMapType() { super(); }

	@Override
	public String getTypeID() {
		return "AkjonavMap";
	}

	@Override
	public AkjonavMapBuilder getBuilder() {
		return new AkjonavMapBuilder();
	}

	@Override
	public Class<AkjonavMap> getBuildableClass() {
		return AkjonavMap.class;
	}
}