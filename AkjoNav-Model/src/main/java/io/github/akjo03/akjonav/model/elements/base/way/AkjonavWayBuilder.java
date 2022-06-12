package io.github.akjo03.akjonav.model.elements.base.way;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElementBuilder;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElementType;
import io.github.akjo03.akjonav.model.elements.base.node.AkjonavNode;
import io.github.akjo03.akjonav.model.elements.base.node.AkjonavNodeBuilder;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuildableType;
import io.validly.Notification;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static io.validly.NoteFirstValidator.valid;

@SuppressWarnings("unused")
public class AkjonavWayBuilder extends AkjonavBaseElementBuilder<AkjonavWay> {
	private List<AkjonavNode> nodes = new ArrayList<>();

	public AkjonavWayBuilder() { super(); }

	public AkjonavWayBuilder(@NotNull BigInteger id) {
		super(id);
	}

	public AkjonavWayBuilder(@NotNull BigInteger id, @NotNull List<AkjonavNode> nodes) {
		super(id);
		this.nodes = nodes;
	}

	public AkjonavWayBuilder setNodes(@NotNull List<AkjonavNode> nodes) {
		this.nodes = nodes;
		return this;
	}

	public AkjonavWayBuilder addNode(@NotNull AkjonavNode node) {
		this.nodes.add(node);
		return this;
	}

	public AkjonavWayBuilder addNodes(@NotNull List<AkjonavNode> nodes) {
		this.nodes.addAll(nodes);
		return this;
	}

	@Override
	protected AkjonavBuildableType getType() {
		return AkjonavBaseElementType.WAY;
	}

	@Override
	protected AkjonavWay buildIt() {
		return new AkjonavWay(elementID, nodes);
	}

	@Override
	protected @NotNull Notification validateElement() {
		Notification notification = new Notification();
		valid(nodes, "AkjonavWay.nodes", notification)
				.mustNotBeNull("Nodes of an AkjonavWay cannot be null!")
				.must(nodesP -> nodesP.size() >= 2, "An AkjonavWay must have at least 2 nodes!");
		return notification;
	}

	@Override
	protected void fromSerialized(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) {
		ArrayNode nodesArray = (ArrayNode) objectNode.get("nodes");
		if (nodesArray != null) {
			for (JsonNode node : nodesArray) {
				this.nodes.add(new AkjonavNodeBuilder().deserialize((ObjectNode) node));
			}
		}
	}
}