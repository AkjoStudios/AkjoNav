package io.github.akjo03.akjonav.model.map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuilder;
import io.validly.Notification;
import org.jetbrains.annotations.NotNull;

import static io.validly.NoteFirstValidator.valid;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class AkjonavMapBuilder extends AkjonavBuilder<AkjonavMapType, AkjonavMap, AkjonavMapBuilder> {
	private String mapID;
	private String mapName;
	private String modelVersion;

	public AkjonavMapBuilder() { super(true); }

	public AkjonavMapBuilder(@NotNull String mapID, @NotNull String mapName, @NotNull String modelVersion) {
		this();
		this.mapID = mapID;
		this.mapName = mapName;
		this.modelVersion = modelVersion;
	}

	public AkjonavMapBuilder setMapID(@NotNull String mapID) {
		this.mapID = mapID;
		return this;
	}

	public AkjonavMapBuilder setMapName(@NotNull String mapName) {
		this.mapName = mapName;
		return this;
	}

	public AkjonavMapBuilder setModelVersion(@NotNull String modelVersion) {
		this.modelVersion = modelVersion;
		return this;
	}

	@Override
	protected AkjonavMapType getType() {
		return AkjonavMapType.TYPE;
	}

	@Override
	protected AkjonavMapBuilderContext getContext() {
		return new AkjonavMapBuilderContext();
	}

	@Override
	protected AkjonavMap buildIt() {
		return new AkjonavMap(mapID, mapName, modelVersion);
	}

	@Override
	protected @NotNull Notification validateIt() {
		Notification notification = new Notification();

		valid(mapID, getValidationID("mapID"), notification)
				.mustNotBeNull("MapID of an AkjonavMap must not be null!");

		valid(mapName, getValidationID("mapName"), notification)
				.mustNotBeNull("MapName of an AkjonavMap must not be null!");

		valid(modelVersion, getValidationID("modelVersion"), notification)
				.mustNotBeNull("ModelVersion of an AkjonavMap must not be null!");

		return notification;
	}

	@Override
	protected void fromSerialized(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) {
		this.mapID = objectNode.get("mapID").asText();
		this.mapName = objectNode.get("mapName").asText();
		this.modelVersion = objectNode.get("modelVersion").asText();
	}
}