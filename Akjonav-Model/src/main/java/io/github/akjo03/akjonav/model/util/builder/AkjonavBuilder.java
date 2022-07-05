package io.github.akjo03.akjonav.model.util.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.services.JsonService;
import io.github.akjo03.akjonav.model.util.validation.ValidationUtil;
import io.validly.Notification;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.validly.NoteFirstValidator.valid;

@Getter
@SuppressWarnings("unused")
public abstract class AkjonavBuilder<E extends AkjonavBuildableType<?>, T extends AkjonavBuildable<E>> {
	@Nullable protected AkjonavBuildableType<?> type;

	private final JsonService jsonService;

	protected AkjonavBuilder() {
		this.type = getType();
		this.jsonService = new JsonService();
	}

	public T build() {
		Notification validationReport = validateIt();

		valid(type, "AkjonavBuildable.type", validationReport)
				.mustNotBeNull("Buildable cannot have a null type!")
				.must(typeP -> typeP.getTypeID() != null, "Buildable type must have a type ID!")
				.must(typeP -> typeP.getBuilder() != null, "Buildable type must have a builder!");

		ValidationUtil.printValidationReport(getClass(), validationReport);
		ValidationUtil.onError(validationReport, notification -> {
			throw new IllegalArgumentException("Buildable is invalid because of " + notification.getMessages().size() + " " + (notification.getMessages().size() == 1 ? "reason" : "reasons") + " (First reason: " + notification.getMessages().values().toArray()[0].toString() + ") | See log for more details.");
		});

		return buildIt();
	}

	public T deserialize(@NotNull ObjectNode objectNode) {
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

	protected void deserializeRootProperties(@NotNull ObjectNode objectNode) {}

	protected abstract AkjonavBuildableType<?> getType();
	protected abstract T buildIt();
	protected abstract @NotNull Notification validateIt();
	protected abstract void fromSerialized(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper);
}