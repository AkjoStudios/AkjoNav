package io.github.akjo03.akjonav.model.util.position;

import io.github.akjo03.akjonav.model.util.builder.AkjonavBuildableType;

public class AkjonavPositionType implements AkjonavBuildableType<AkjonavPositionType, AkjonavPosition, AkjonavPositionBuilder> {
	public static final AkjonavPositionType TYPE = new AkjonavPositionType();

	private AkjonavPositionType() { super(); }

	@Override
	public String getTypeID() {
		return "AkjonavPosition";
	}

	@Override
	public AkjonavPositionBuilder getBuilder() {
		return new AkjonavPositionBuilder();
	}

	@Override
	public Class<AkjonavPosition> getBuildableClass() {
		return AkjonavPosition.class;
	}
}