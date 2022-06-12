package io.github.akjo03.akjonav.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.akjo03.akjonav.model.constants.AkjonavModelConstants;
import io.github.akjo03.akjonav.model.elements.base.area.AkjonavAreaBuilder;
import io.github.akjo03.akjonav.model.elements.base.node.AkjonavNodeBuilder;
import io.github.akjo03.akjonav.model.elements.base.way.AkjonavWayBuilder;
import io.github.akjo03.akjonav.model.map.AkjonavMap;
import io.github.akjo03.akjonav.model.map.AkjonavMapBuilder;
import io.github.akjo03.akjonav.model.map.file.AkjonavMapReader;
import io.github.akjo03.akjonav.model.map.file.AkjonavMapWriter;
import io.github.akjo03.akjonav.model.util.position.AkjonavPositionBuilder;
import io.github.akjo03.util.logging.v2.Logger;
import io.github.akjo03.util.logging.v2.LoggerManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class AkjonavModelApp implements CommandLineRunner {
	private static final Logger LOGGER = LoggerManager.getLogger(AkjonavModelApp.class, AkjonavModelConstants.LOGGING_LEVEL);

	private final ApplicationContext applicationContext;
	private final ObjectMapper objectMapper;

	public static void main(String[] args) {
		LOGGER.setLoggingFormat(AkjonavModelConstants.LOGGING_FORMAT);
		SpringApplication.run(AkjonavModelApp.class, args);
	}

	@Override
	public void run(String[] args) {
		LOGGER.info("Running " + AkjonavModelConstants.APP_NAME + " V" + AkjonavModelConstants.APP_VERSION + "...");

		AkjonavMapBuilder mapBuilder = new AkjonavMapBuilder();

		mapBuilder.addBaseElements(List.of(
				new AkjonavNodeBuilder(BigInteger.valueOf(1))
						.setPosition(new AkjonavPositionBuilder(0.0, 0.0).build())
						.build(),
				new AkjonavNodeBuilder(BigInteger.valueOf(1))
						.setPosition(new AkjonavPositionBuilder(1.0, 1.0).build())
						.build(),
				new AkjonavNodeBuilder(BigInteger.valueOf(3))
						.setPosition(new AkjonavPositionBuilder(2.0, 2.0).build())
						.build()
		)).addBaseElement(
				new AkjonavWayBuilder(BigInteger.valueOf(4))
						.addNodes(List.of(
								mapBuilder.getBaseElementReference(BigInteger.valueOf(1)),
								mapBuilder.getBaseElementReference(BigInteger.valueOf(2)),
								mapBuilder.getBaseElementReference(BigInteger.valueOf(3))
						)).build()
		).addBaseElement(
				new AkjonavAreaBuilder(BigInteger.valueOf(5))
						.addNodes(List.of(
								mapBuilder.getBaseElementReference(BigInteger.valueOf(1)),
								mapBuilder.getBaseElementReference(BigInteger.valueOf(2)),
								mapBuilder.getBaseElementReference(BigInteger.valueOf(1))
						)).build()
		);

		AkjonavMap map = mapBuilder.build();

		AkjonavMapWriter mapWriter = new AkjonavMapWriter(AkjonavModelConstants.MAPS_FOLDER.resolve("testMap.anm"));
		try {
			mapWriter.writeMap(map, objectMapper);
		} catch (IOException e) {
			LOGGER.error("Error writing map to file!", e);
		}

		AkjonavMapReader mapReader = new AkjonavMapReader(AkjonavModelConstants.MAPS_FOLDER.resolve("testMap.anm"));
		try {
			AkjonavMap readMap = mapReader.readMap(objectMapper);
			LOGGER.info("Read map: " + readMap);
		} catch (IOException e) {
			LOGGER.error("Error reading map from file!", e);
		}

		exit(0);
	}

	public void exit(int exitCode) {
		LOGGER.info("Exiting " + AkjonavModelConstants.APP_NAME + " with status code " + exitCode + "...");
		System.exit(SpringApplication.exit(applicationContext, () -> exitCode));
		throw new IllegalStateException("SpringApplication.exit() should have exited the application!");
	}
}