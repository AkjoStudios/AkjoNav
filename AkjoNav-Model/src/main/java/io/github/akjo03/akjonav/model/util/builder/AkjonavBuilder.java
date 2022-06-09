package io.github.akjo03.akjonav.model.util.builder;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.util.validation.ValidationUtil;
import io.validly.Notification;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import static io.validly.NoteFirstValidator.valid;

@Getter
@Component
@SuppressWarnings("unused")
public abstract class AkjonavBuilder<T extends AkjonavBuildable> {
	@Nullable protected AkjonavBuildableType type;

	protected AkjonavBuilder() {
		this.type = getType();
	}

	public T build() {
		Notification validationReport = validateIt();

		valid(type, "AkjonavBuildable.type", validationReport)
				.mustNotBeNull("Buildable cannot have a null type!")
				.must(type -> type.getTypeID() != null, "Buildable type must have a type ID!")
				.must(type -> type.getBuilder() != null, "Buildable type must have a builder!");

		ValidationUtil.printValidationReport(AkjonavBuilder.class, validationReport);
		ValidationUtil.onError(validationReport, (notification) -> {
			throw new IllegalArgumentException("Buildable is invalid because of " + notification.getMessages().size() + " reasons (First reason: " + notification.getMessages().values().toArray()[0].toString() + ") | See log for more details.");
		});

		return buildIt();
	}

	public T deserialize(@NotNull ObjectNode objectNode) {
		this.type = getType();
		fromSerialized((ObjectNode) objectNode.get("data"));
		return build();
	}

	protected abstract @NotNull AkjonavBuildableType getType();
	protected abstract T buildIt();
	protected abstract @NotNull Notification validateIt();
	protected abstract void fromSerialized(@NotNull ObjectNode objectNode);
}