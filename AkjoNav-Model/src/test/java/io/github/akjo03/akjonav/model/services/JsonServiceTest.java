package io.github.akjo03.akjonav.model.services;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration(classes = { JsonService.class })
class JsonServiceTest {
	private final JsonService jsonService;

	@Autowired
	public JsonServiceTest(JsonService jsonService) {
		this.jsonService = jsonService;
	}

	@Test
	void testObjectMapper() {
		assertNotNull(jsonService.getObjectMapper());
		assertTrue(jsonService.getObjectMapper().getRegisteredModuleIds().contains(new Jdk8Module().getTypeId()));
		assertTrue(jsonService.getObjectMapper().getRegisteredModuleIds().contains(new JavaTimeModule().getTypeId()));
	}
}