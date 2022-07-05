package io.github.akjo03.akjonav.model.elements;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuilder;
import io.validly.Notification;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

import static io.validly.NoteFirstValidator.valid;

@SuppressWarnings("unused")
public abstract class AkjonavElementBuilder<E extends AkjonavElementType<?>, T extends AkjonavElement<E>> extends AkjonavBuilder<E, T> {
	protected BigInteger elementID;

	protected AkjonavElementBuilder() { super(); }

	protected AkjonavElementBuilder(BigInteger elementID) {
		this.elementID = elementID;
	}

	public AkjonavElementBuilder<E, T> setID(BigInteger elementID) {
		this.elementID = elementID;
		return this;
	}

	@Override
	protected @NotNull Notification validateIt() {
		Notification notification = validateElement();

		valid(elementID, "AkjonavElement.elementID", notification)
				.mustNotBeNull("ID of an AkjonavElement cannot be null!")
				.must(elementIDP -> elementIDP.compareTo(BigInteger.ZERO) > 0, "ID of an AkjonavElement must be greater than 0!");

		return notification;
	}

	@Override
	protected void deserializeRootProperties(@NotNull ObjectNode objectNode) {
		this.elementID = new BigInteger(objectNode.get("id").asText());
	}

	protected abstract @NotNull Notification validateElement();
}