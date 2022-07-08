package io.github.akjo03.akjonav.model.util.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.services.JsonService;
import io.github.akjo03.akjonav.model.util.validation.ValidationUtil;
import io.validly.Notification;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import static io.validly.NoteFirstValidator.valid;

@Getter
@SuppressWarnings("unused")
public abstract class AkjonavBuilder<T extends AkjonavBuildableType<T, E, B>, E extends AkjonavBuildable<T, E , B>, B extends AkjonavBuilder<T, E, B>> {
	protected AkjonavBuilderContext<T, E, B> context;
	protected T type;
	protected boolean hasContext = false;

	private final JsonService jsonService;

	protected AkjonavBuilder() {
		this.type = getType();
		this.jsonService = new JsonService();
	}

	protected AkjonavBuilder(boolean hasContext) {
		this();
		this.hasContext = hasContext;
	}

	public void setContext(AkjonavBuilderContext<T, E, B> context) throws IllegalStateException {
		if (!hasContext) {
			throw new IllegalStateException("Context cannot be set on non-context builder!");
		}
		Class<?> callerClass = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
		if (!AkjonavBuilderContext.class.isAssignableFrom(callerClass)) {
			throw new IllegalStateException("Cannot set context from outside of an AkjonavBuilderContext!");
		}
		this.context = context;
	}

	public AkjonavBuilderContext<T, E, B> finish() {
		if (!hasContext) {
			throw new IllegalStateException("Builder cannot be finished as it is a non-context builder!");
		}
		if (context == null) {
			throw new IllegalStateException("Cannot finish builder without context!");
		}
		context.finishContext(build());
		return context;
	}

	public E build() {
		Class<?> callerClass = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
		if (hasContext && context == null && !AkjonavBuilder.class.isAssignableFrom(callerClass)) {
			throw new IllegalStateException("Cannot build " + getType().getBuildableClass().getSimpleName() + " without context!");
		}

		Notification validationReport = validateIt();

		valid(type, "AkjonavBuildable.type", validationReport)
				.mustNotBeNull("Buildable cannot have a null type!")
				.must(typeP -> typeP.getTypeID() != null, "Buildable type must have a type ID!")
				.must(typeP -> typeP.getBuilder() != null, "Buildable type must have a builder!");

		ValidationUtil.printValidationReport(getClass(), validationReport);
		ValidationUtil.onError(validationReport, notification -> {
			throw new IllegalArgumentException(getClass().getSimpleName() + " is invalid because of " + notification.getMessages().size() + " " + (notification.getMessages().size() == 1 ? "reason" : "reasons") + " (First reason: " + notification.getMessages().values().toArray()[0].toString() + ") | See log for more details.");
		});

		return buildIt();
	}

	public E deserialize(@NotNull ObjectNode objectNode) {
		this.context = getContext();
		deserializeRootProperties(objectNode);

		this.type = getType();
		if (objectNode.get("type") == null || this.type == null || !this.type.getTypeID().equals(objectNode.get("type").asText())) {
			throw new IllegalArgumentException("Type ID of deserialized object is invalid or does not match type ID of builder!");
		}
		ObjectNode dataNode = (ObjectNode) objectNode.get("data");
		if (dataNode == null) {
			throw new IllegalArgumentException("Data of a buildable cannot be null!");
		}

		fromSerialized(dataNode, jsonService.getObjectMapper());
		return build();
	}

	protected String getValidationID(String propertyName) {
		return getType().getBuildableClass().getSimpleName() + "." + propertyName;
	}

	protected void deserializeRootProperties(@NotNull ObjectNode objectNode) { }
	protected abstract T getType();
	protected abstract AkjonavBuilderContext<T, E, B> getContext();
	protected abstract E buildIt();
	protected abstract @NotNull Notification validateIt();
	protected abstract void fromSerialized(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper);
}