package io.github.akjo03.akjonav.model.util.position;

import io.github.akjo03.akjonav.model.util.builder.AkjonavBuildableType;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuilder;

public class AkjonavPositionType implements AkjonavBuildableType {
	public static final AkjonavPositionType type = new AkjonavPositionType();

	private static final String TYPE_ID = "AkjonavPosition";
	private static final AkjonavPositionBuilder BUILDER = new AkjonavPositionBuilder();

	private AkjonavPositionType() {}

	@Override
	public String getTypeID() { return TYPE_ID; }

	@Override
	public AkjonavBuilder<?, ?> getBuilder() { return BUILDER; }

	@Override
	public String toString() {
		return TYPE_ID;
	}
}