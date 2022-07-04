package io.github.akjo03.akjonav.base.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuildable;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuildableType;
import io.github.akjo03.akjonav.model.util.builder.AkjonavBuilder;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class ModelConverterService {
	private final JsonService jsonService;

	public <T extends AkjonavBuildableType<B>, B extends AkjonavBuildable<T>> B convertData(HashMap<?, ?> data, @NotNull AkjonavBuilder<T, B> builder) {
		return builder.deserialize((ObjectNode) jsonService.getObjectMapper().convertValue(data, JsonNode.class));
	}
}