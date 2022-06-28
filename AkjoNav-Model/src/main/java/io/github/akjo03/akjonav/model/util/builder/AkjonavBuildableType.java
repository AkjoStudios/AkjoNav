package io.github.akjo03.akjonav.model.util.builder;

public interface AkjonavBuildableType<T extends AkjonavBuildable<?>> {
	String getTypeID();
	AkjonavBuilder<?, ?> getBuilder();
	Class<? extends T> getTypeClass();
}