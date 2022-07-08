package io.github.akjo03.akjonav.model.util.builder;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public abstract class AkjonavBuilderContext<T extends AkjonavBuildableType<T, E, B>, E extends AkjonavBuildable<T, E, B>, B extends AkjonavBuilder<T, E, B>> {
	protected B builder;
	protected E buildable;

	protected AkjonavBuilderContext() { super(); }

	protected @NotNull B startContext(@NotNull B builder) {
		builder.setContext(this);
		this.builder = builder;
		return builder;
	}

	protected AkjonavBuilderContext<T, E, B> finishContext(E buildable) {
		this.buildable = buildable;
		return this;
	}

	public E retrieve() {
		return buildable;
	}
}