package io.github.akjo03.akjonav.model.elements.base.node;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElementBuilder;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElementType;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuildableType;
import io.validly.Notification;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

@SuppressWarnings("unused")
public class AkjonavNodeBuilder extends AkjonavBaseElementBuilder<AkjonavNode> {
	public AkjonavNodeBuilder() { super(); }

	public AkjonavNodeBuilder(@NotNull BigInteger id) {
		super(id);
	}

	@Override
	protected AkjonavBuildableType getType() {
		return AkjonavBaseElementType.NODE;
	}

	@Override
	protected AkjonavNode buildIt() {
		return new AkjonavNode(elementID);
	}

	@Override
	protected @NotNull Notification validateElement() {
		return new Notification();
	}

	@Override
	protected void fromSerializedElement(@NotNull ObjectNode objectNode) {
		// Not implemented yet
	}
}