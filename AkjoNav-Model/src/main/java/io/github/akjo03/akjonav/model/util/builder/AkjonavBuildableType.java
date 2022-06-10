package io.github.akjo03.akjonav.model.util.builder;

public interface AkjonavBuildableType {
	String getTypeID();
	AkjonavBuilder<?, ?> getBuilder();
}