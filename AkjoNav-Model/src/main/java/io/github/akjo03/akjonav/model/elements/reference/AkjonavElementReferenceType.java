package io.github.akjo03.akjonav.model.elements.reference;

import io.github.akjo03.akjonav.model.util.builder.AkjonavBuildableType;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuilder;

public class AkjonavElementReferenceType implements AkjonavBuildableType {
	public static final AkjonavElementReferenceType type = new AkjonavElementReferenceType();

	private static final String TYPE_ID = "AkjonavElementReference";
	private static final AkjonavElementReferenceBuilder BUILDER = new AkjonavElementReferenceBuilder();

	private AkjonavElementReferenceType() {}

	@Override
	public String getTypeID() {
		return TYPE_ID;
	}

	@Override
	public AkjonavBuilder<?, ?> getBuilder() {
		return BUILDER;
	}

	@Override
	public String toString() {
		return TYPE_ID;
	}
}