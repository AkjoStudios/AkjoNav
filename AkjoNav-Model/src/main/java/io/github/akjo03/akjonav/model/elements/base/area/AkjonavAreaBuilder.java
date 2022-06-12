package io.github.akjo03.akjonav.model.elements.base.area;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElementBuilder;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElementType;
import io.github.akjo03.akjonav.model.elements.reference.AkjonavElementReference;
import io.github.akjo03.akjonav.model.elements.reference.AkjonavElementReferenceBuilder;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuildableType;
import io.validly.Notification;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static io.validly.NoteFirstValidator.valid;

@SuppressWarnings("unused")
public class AkjonavAreaBuilder extends AkjonavBaseElementBuilder<AkjonavArea> {
	private List<AkjonavElementReference> nodeRefs = new ArrayList<>();

	public AkjonavAreaBuilder() { super(); }

	public AkjonavAreaBuilder(@NotNull BigInteger id) {
		super(id);
	}

	public AkjonavAreaBuilder(@NotNull BigInteger id, @NotNull List<AkjonavElementReference> nodeRefs) {
		super(id);
		this.nodeRefs = nodeRefs;
	}

	public AkjonavAreaBuilder setNodes(@NotNull List<AkjonavElementReference> nodeRefs) {
		this.nodeRefs = nodeRefs;
		return this;
	}

	public AkjonavAreaBuilder addNode(@NotNull AkjonavElementReference nodeRef) {
		this.nodeRefs.add(nodeRef);
		return this;
	}

	public AkjonavAreaBuilder addNodes(@NotNull List<AkjonavElementReference> nodeRefs) {
		this.nodeRefs.addAll(nodeRefs);
		return this;
	}

	@Override
	protected AkjonavBuildableType getType() {
		return AkjonavBaseElementType.AREA;
	}

	@Override
	protected AkjonavArea buildIt() {
		return new AkjonavArea(elementID, nodeRefs);
	}

	@Override
	protected @NotNull Notification validateElement() {
		Notification notification = new Notification();
		valid(nodeRefs, "AkjonavArea.nodeRefs", notification)
				.mustNotBeNull("Nodes of an AkjonavArea cannot be null!")
				.must(nodesRefP -> nodesRefP.size() >= 3, "An AkjonavArea must have at least 2 nodes!")
				.must(nodesRefP -> nodesRefP.stream().filter(nodeRef -> nodeRef.getElementType().equals(AkjonavBaseElementType.NODE)).count() == nodesRefP.size(), "All element references of an AkjonavWay must be of type BaseElement:NODE!")
				.must(nodesRefP -> nodesRefP.get(0).equals(nodesRefP.get(nodesRefP.size() - 1)), "The first and last node of an AkjonavArea must be the same!");
		return notification;
	}

	@Override
	protected void fromSerialized(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) {
		ArrayNode nodesArray = (ArrayNode) objectNode.get("nodeRefs");
		if (nodesArray != null) {
			for (JsonNode node : nodesArray) {
				this.nodeRefs.add(new AkjonavElementReferenceBuilder().deserialize((ObjectNode) node));
			}
		}
	}
}