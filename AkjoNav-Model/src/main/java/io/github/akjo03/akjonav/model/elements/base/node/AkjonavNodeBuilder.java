package io.github.akjo03.akjonav.model.elements.base.node;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElementBuilder;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElementType;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuildableType;
import io.github.akjo03.akjonav.model.util.position.AkjonavPosition;
import io.github.akjo03.akjonav.model.util.position.AkjonavPositionBuilder;
import io.validly.Notification;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

import static io.validly.NoteFirstValidator.valid;

@SuppressWarnings("unused")
public class AkjonavNodeBuilder extends AkjonavBaseElementBuilder<AkjonavNode> {
	private AkjonavPosition position;

	public AkjonavNodeBuilder() { super(); }

	public AkjonavNodeBuilder(@NotNull BigInteger id) {
		super(id);
	}

	public AkjonavNodeBuilder(@NotNull BigInteger id, @NotNull AkjonavPosition position) {
		super(id);
		this.position = position;
	}

	public AkjonavNodeBuilder setPosition(@NotNull AkjonavPosition position) {
		this.position = position;
		return this;
	}

	@Override
	protected AkjonavBuildableType getType() {
		return AkjonavBaseElementType.NODE;
	}

	@Override
	protected AkjonavNode buildIt() {
		return new AkjonavNode(elementID, position);
	}

	@Override
	protected @NotNull Notification validateElement() {
		Notification notification = new Notification();
		valid(position, "AkjonavNode.position", notification)
				.mustNotBeNull("Position of an AkjonavNode cannot be null!");
		return notification;
	}

	@Override
	protected void fromSerialized(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) {
		this.position = new AkjonavPositionBuilder().deserialize((ObjectNode) objectNode.get("position"));
	}
}