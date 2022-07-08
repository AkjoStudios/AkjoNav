package io.github.akjo03.akjonav.model.map;

import io.github.akjo03.akjonav.model.util.builder.AkjonavBuilderContext;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class AkjonavMapBuilderContext extends AkjonavBuilderContext<AkjonavMapType, AkjonavMap, AkjonavMapBuilder> {
	public AkjonavMapBuilderContext() { super(); }

	public AkjonavMapBuilder start(@NotNull String mapID, @NotNull String mapName, @NotNull String modelVersion) {
		AkjonavMapBuilder builder = new AkjonavMapBuilder();
		builder.setContext(this);
		builder.setMapID(mapID);
		builder.setMapName(mapName);
		builder.setModelVersion(modelVersion);
		return startContext(builder);
	}

	public AkjonavBuilderContext<AkjonavMapType, AkjonavMap, AkjonavMapBuilder> finish(AkjonavMap map) {
		return finishContext(map);
	}
}