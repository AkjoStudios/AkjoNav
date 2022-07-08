package io.github.akjo03.akjonav.model.map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuildable;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class AkjonavMap extends AkjonavBuildable<AkjonavMapType, AkjonavMap, AkjonavMapBuilder> {
	@NotNull private final String mapID;
	@NotNull private final String mapName;
	@NotNull private final String modelVersion;

	protected AkjonavMap(@NotNull String mapID, @NotNull String mapName, @NotNull String modelVersion) {
		super(AkjonavMapType.TYPE);
		this.mapID = mapID;
		this.mapName = mapName;
		this.modelVersion = modelVersion;
	}

	@Override
	protected @NotNull ObjectNode serializeIt(@NotNull ObjectNode objectNode, @NotNull ObjectMapper objectMapper) {
		objectNode.put("mapID", mapID);
		objectNode.put("mapName", mapName);
		objectNode.put("modelVersion", modelVersion);
		return objectNode;
	}

	@Override
	protected @NotNull String toObjectString() {
		return "{mapID=" + mapID + ", mapName=" + mapName + ", modelVersion=" + modelVersion + "}";
	}
}