package io.github.akjo03.akjonav.model.util.builder;

public interface AkjonavBuildableType<T extends AkjonavBuildableType<T, E, B>, E extends AkjonavBuildable<T, E, B>, B extends AkjonavBuilder<T, E, B>> {
	String getTypeID();
	B getBuilder();
	Class<E> getBuildableClass();
}