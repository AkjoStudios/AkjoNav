package io.github.akjo03.akjonav.desktopmapper.managers;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.akjo03.akjonav.desktopmapper.services.JsonService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DataManager {
	private static final Path DATA_DIR = Path.of(System.getenv("APPDATA"), "Akjonav", "DesktopMapper");
	private static boolean initialized = false;

	public static void initialize() throws IOException {
		Files.createDirectories(DATA_DIR);
		initialized = true;
	}

	public static void save(String path, JsonNode node) throws IOException {
		if (!initialized) {
			throw new IllegalStateException("DataManager not initialized!");
		}
		Path fullPath = Path.of(DATA_DIR.toString(), path);
		if (!Files.exists(fullPath)) {
			Files.createFile(fullPath);
		}
		JsonService.getObjectMapper().writeValue(fullPath.toFile(), node);
	}

	public static boolean exists(String path) {
		if (!initialized) {
			throw new IllegalStateException("DataManager not initialized!");
		}
		Path fullPath = Path.of(DATA_DIR.toString(), path);
		return Files.exists(fullPath);
	}

	public static JsonNode load(String path) throws IOException {
		if (!initialized) {
			throw new IllegalStateException("DataManager not initialized!");
		}
		Path fullPath = Path.of(DATA_DIR.toString(), path);
		return JsonService.getObjectMapper().readTree(fullPath.toFile());
	}
}