package io.github.akjo03.akjonav.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.akjo03.akjonav.model.constants.AkjonavModelConstants;
import io.github.akjo03.akjonav.model.elements.base.AkjonavBaseElementBuilder;
import io.github.akjo03.akjonav.model.elements.base.node.AkjonavNodeBuilder;
import io.github.akjo03.akjonav.model.elements.base.way.AkjonavWay;
import io.github.akjo03.akjonav.model.elements.base.way.AkjonavWayBuilder;
import io.github.akjo03.akjonav.model.util.position.AkjonavPositionBuilder;
import io.github.akjo03.util.logging.v2.Logger;
import io.github.akjo03.util.logging.v2.LoggerManager;
import io.github.akjo03.util.math.unit.units.length.Length;
import io.github.akjo03.util.math.unit.units.length.LengthUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class AkjonavModelApp implements CommandLineRunner {
	private static final Logger LOGGER = LoggerManager.getLogger(AkjonavModelApp.class, AkjonavModelConstants.LOGGING_LEVEL);

	private final ApplicationContext applicationContext;
	private final ObjectMapper objectMapper;

	// ------------------------------------------------ BOARD OF THINGS THAT SHOULD BE DONE (will be converted into issues soon) ------------------------------------------------
	// TODO: 1. Add the AkjonavMap class
	// TODO: 2. Add a reference system so that every AkjonavElement can be referenced by a unique ID from a AkjonavMap
	// TODO: 3. Instead of using a list of AkjonavNodes to represent a way, use the reference to a way from an AkjonavMap
	// TODO: 4. Add an AkjonavArea which is basically an AkjonavWay that has the additional validation that it is closed and has a minimum of 3 nodes
	// TODO: 5. Add different AkjonavMapElements which wrap around certain types of AkjonavBaseElements (as references) to add to the AkjonavMap

	public static void main(String[] args) {
		LOGGER.setLoggingFormat(AkjonavModelConstants.LOGGING_FORMAT);
		SpringApplication.run(AkjonavModelApp.class, args);
	}

	@Override
	public void run(String[] args) {
		LOGGER.info("Running " + AkjonavModelConstants.APP_NAME + " V" + AkjonavModelConstants.APP_VERSION + "...");

		AkjonavWay akjonavWay = new AkjonavWayBuilder(BigInteger.valueOf(3))
				.addNodes(List.of(
						new AkjonavNodeBuilder(BigInteger.valueOf(1))
								.setPosition(new AkjonavPositionBuilder(0.0, 0.0, new Length(new BigDecimal("100"), LengthUnit.METRE)).build())
								.build(),
						new AkjonavNodeBuilder(BigInteger.valueOf(2))
								.setPosition(new AkjonavPositionBuilder(1.0, 1.0, new Length(new BigDecimal("150"), LengthUnit.METRE)).build())
								.build()
				)).build();
		LOGGER.info("AkjonavWay: " + akjonavWay);

		ObjectNode serializedWay = akjonavWay.serialize(objectMapper);
		LOGGER.info("Serialized AkjonavWay: " + serializedWay);

		AkjonavWay deserializedWay = AkjonavBaseElementBuilder.deserializeElement(serializedWay, AkjonavWay.class);
		LOGGER.info("Deserialized AkjonavWay: " + deserializedWay);

		exit(0);
	}

	public void exit(int exitCode) {
		LOGGER.info("Exiting " + AkjonavModelConstants.APP_NAME + " with status code " + exitCode + "...");
		System.exit(SpringApplication.exit(applicationContext, () -> exitCode));
		throw new IllegalStateException("SpringApplication.exit() should have exited the application!");
	}
}