package io.github.akjo03.akjonav.model.map.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.akjo03.akjonav.model.map.AkjonavMap;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
public class AkjonavMapWriter {
	private final Path mapFilePath;

	public void writeMap(@NotNull AkjonavMap map, @NotNull ObjectMapper objectMapper) throws IOException {
		Files.createDirectories(mapFilePath.getParent());
		if (Files.exists(mapFilePath)) {
			Files.delete(mapFilePath);
		}
		Files.createFile(mapFilePath);
		objectMapper.writeValue(mapFilePath.toFile(), map.serialize(objectMapper));
	}
}