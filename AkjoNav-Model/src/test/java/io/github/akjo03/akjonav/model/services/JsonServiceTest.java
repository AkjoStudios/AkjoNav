package io.github.akjo03.akjonav.model.services;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonServiceTest {
	private final JsonService jsonService;

	public JsonServiceTest() {
		this.jsonService = new JsonService();
	}

	@Test
	void testObjectMapper() {
		assertNotNull(jsonService.getObjectMapper());
		assertTrue(jsonService.getObjectMapper().getRegisteredModuleIds().contains(new Jdk8Module().getTypeId()));
		assertTrue(jsonService.getObjectMapper().getRegisteredModuleIds().contains(new JavaTimeModule().getTypeId()));
	}
}