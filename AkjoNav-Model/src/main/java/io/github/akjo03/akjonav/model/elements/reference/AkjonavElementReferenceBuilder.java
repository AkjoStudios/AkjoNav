package io.github.akjo03.akjonav.model.elements.reference;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.elements.AkjonavElementType;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElementType;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuildableType;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuilder;
import io.validly.Notification;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

import static io.validly.NoteFirstValidator.valid;

@SuppressWarnings("unused")
public class AkjonavElementReferenceBuilder extends AkjonavBuilder<AkjonavElementReferenceType, AkjonavElementReference> {
	private AkjonavElementType elementType;
	private BigInteger elementID;

	public AkjonavElementReferenceBuilder() { super(); }

	public AkjonavElementReferenceBuilder(@NotNull AkjonavElementType elementType, @NotNull BigInteger elementID) {
		super();
		this.elementType = elementType;
		this.elementID = elementID;
	}

	public AkjonavElementReferenceBuilder setElementType(@NotNull AkjonavElementType elementType) {
		this.elementType = elementType;
		return this;
	}

	public AkjonavElementReferenceBuilder setElementID(@NotNull BigInteger elementID) {
		this.elementID = elementID;
		return this;
	}

	@Override
	protected AkjonavBuildableType getType() {
		return AkjonavElementReferenceType.type;
	}

	@Override
	protected AkjonavElementReference buildIt() {
		StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).walk(stackFrames -> {
			stackFrames.filter(stackFrame -> {
				String className = stackFrame.getClassName().split("\\.")[stackFrame.getClassName().split("\\.").length - 1];
				return (className.equals("AkjonavMapBuilder"));
			}).findFirst().orElseThrow(() -> new IllegalStateException("An AkjonavElementReference can only be created from within an AkjonavMap or an AkjonavMapBuilder!"));
			return null;
		});
		return new AkjonavElementReference(elementType, elementID);
	}

	@Override
	protected @NotNull Notification validateIt() {
		Notification notification = new Notification();

		valid(elementType, "AkjonavElementReference.elementType", notification)
				.mustNotBeNull("Type of a referenced AkjonavElement cannot be null!");

		valid(elementID, "AkjonavElementReference.elementID", notification)
				.mustNotBeNull("ID of a referenced AkjonavElement cannot be null!")
				.must(elementIDP -> elementIDP.compareTo(BigInteger.ZERO) > 0, "ID of a referenced AkjonavElement must be greater than 0!");

		return notification;
	}

	@Override
	protected void fromSerialized(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) {
		String serializedElementType = objectNode.get("elementType").asText().split(":")[0];
		switch (serializedElementType) {
			case "BaseElement" -> this.elementType = AkjonavBaseElementType.fromType(objectNode.get("elementType").asText());
			default -> throw new IllegalArgumentException("Cannot generate element reference from unknown element type: " + serializedElementType + "!");
		}
		this.elementID = new BigInteger(objectNode.get("elementID").asText());
	}
}