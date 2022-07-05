package io.github.akjo03.akjonav.desktopmapper.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.akjo03.util.json.JsonPrettyPrinter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonService {
	public static ObjectMapper getObjectMapper() {
		return new ObjectMapper()
				.setDefaultPrettyPrinter(new JsonPrettyPrinter())
				.registerModule(new Jdk8Module())
				.registerModule(new JavaTimeModule());
	}
}