package io.github.akjo03.akjonav.model.util.builder;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.util.validation.ValidationUtil;
import io.validly.Notification;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

import static io.validly.NoteFirstValidator.valid;

@Getter
@Component
public abstract class AkjonavBuilder<T extends AkjonavBuildable> {
	protected BigInteger id;
	protected AkjonavBuildableType type;

	protected AkjonavBuilder() {
		this.id = null;
		this.type = null;
	}

	protected AkjonavBuilder(BigInteger id) {
		this.id = id;
		this.type = getType();
	}

	public T build() {
		Notification validationReport = validateIt();

		valid(id, "AkjonavBuildable.id", validationReport)
				.mustNotBeNull("Buildable cannot have a null id!")
				.must(id -> id.compareTo(BigInteger.ZERO) > 0, "ID of a buildable must be greater than zero!");
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
		this.id = new BigInteger(objectNode.get("id").asText());
		this.type = getType();
		fromSerialized((ObjectNode) objectNode.get("data"));
		return build();
	}

	protected abstract AkjonavBuildableType getType();
	protected abstract T buildIt();
	protected abstract Notification validateIt();
	protected abstract void fromSerialized(ObjectNode objectNode);
}