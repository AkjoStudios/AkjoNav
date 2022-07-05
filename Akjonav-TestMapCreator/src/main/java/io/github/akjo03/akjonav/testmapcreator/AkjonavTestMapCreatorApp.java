package io.github.akjo03.akjonav.testmapcreator;

import io.github.akjo03.akjonav.model.elements.base.node.AkjonavNodeBuilder;
import io.github.akjo03.akjonav.model.elements.base.way.AkjonavWayBuilder;
import io.github.akjo03.akjonav.model.elements.map.highway.AkjonavHighwayBuilder;
import io.github.akjo03.akjonav.model.map.AkjonavMap;
import io.github.akjo03.akjonav.model.map.AkjonavMapBuilder;
import io.github.akjo03.akjonav.model.map.file.AkjonavMapReader;
import io.github.akjo03.akjonav.model.map.file.AkjonavMapWriter;
import io.github.akjo03.akjonav.model.util.position.AkjonavPositionBuilder;
import io.github.akjo03.akjonav.testmapcreator.constants.AkjonavTestMapCreatorConstants;
import io.github.akjo03.akjonav.testmapcreator.services.JsonService;
import io.github.akjo03.util.logging.v2.Logger;
import io.github.akjo03.util.logging.v2.LoggerManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.math.BigInteger;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
@RequiredArgsConstructor
@ConfigurationPropertiesScan
public class AkjonavTestMapCreatorApp implements CommandLineRunner {
	private static final Logger LOGGER = LoggerManager.getLogger(AkjonavTestMapCreatorApp.class)
			.setMinimumLoggingLevel(AkjonavTestMapCreatorConstants.LOGGING_LEVEL)
			.setLoggingFormat(AkjonavTestMapCreatorConstants.LOGGING_FORMAT);

	private final JsonService jsonService;

	public static void main(String[] args) {
		SpringApplication.run(AkjonavTestMapCreatorApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("Running TestMapCreator...");

		AkjonavMapBuilder mapBuilder = new AkjonavMapBuilder();
		AkjonavMap map = mapBuilder
				.addBaseElements(List.of(
						new AkjonavNodeBuilder(BigInteger.valueOf(1))
								.setPosition(new AkjonavPositionBuilder(46.8938422, 7.3298003).build())
								.build(),
						new AkjonavNodeBuilder(BigInteger.valueOf(2))
								.setPosition(new AkjonavPositionBuilder(46.8937608, 7.3298296).build())
								.build()
				)).addBaseElement(
						new AkjonavWayBuilder(BigInteger.valueOf(3))
								.addNodes(List.of(
										Objects.requireNonNull(mapBuilder.getBaseElementReference(BigInteger.valueOf(1))),
										Objects.requireNonNull(mapBuilder.getBaseElementReference(BigInteger.valueOf(2)))
								)).build()
				).addMapElement(
						new AkjonavHighwayBuilder(BigInteger.valueOf(1))
								.addElementRef(mapBuilder.getBaseElementReference(BigInteger.valueOf(3)))
				).build();
		AkjonavMapWriter mapWriter = new AkjonavMapWriter(Path.of("maps/testmap.anm"));
		mapWriter.writeMap(map, jsonService.getObjectMapper());

		AkjonavMapReader mapReader = new AkjonavMapReader(Path.of("maps/testmap.anm"));
		AkjonavMap readMap = mapReader.readMap(jsonService.getObjectMapper());
		System.out.println(readMap);

		LOGGER.info("Finished TestMapCreator!");
	}
}