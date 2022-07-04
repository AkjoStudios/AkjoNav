package io.github.akjo03.akjonav.base.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.akjo03.util.json.JsonPrettyPrinter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class JsonService {
	@Bean
	public ObjectMapper getObjectMapper() {
		return new ObjectMapper()
				.setDefaultPrettyPrinter(new JsonPrettyPrinter())
				.registerModule(new Jdk8Module())
				.registerModule(new JavaTimeModule());
	}
}