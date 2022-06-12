package io.github.akjo03.akjonav.model.map.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.map.AkjonavMap;
import io.github.akjo03.akjonav.model.map.AkjonavMapBuilder;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;

@RequiredArgsConstructor
public class AkjonavMapReader {
	private final Path mapFilePath;

	public AkjonavMap readMap(@NotNull ObjectMapper objectMapper) throws IOException {
		AkjonavMapBuilder mapBuilder = new AkjonavMapBuilder();
		mapBuilder.deserialize((ObjectNode) objectMapper.readTree(mapFilePath.toFile()));
		return mapBuilder.build();
	}
}