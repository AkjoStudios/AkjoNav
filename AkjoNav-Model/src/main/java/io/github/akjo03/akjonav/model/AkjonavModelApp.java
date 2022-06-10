package io.github.akjo03.akjonav.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.akjo03.akjonav.model.constants.AkjonavModelConstants;
import io.github.akjo03.akjonav.model.elements.base.node.AkjonavNodeBuilder;
import io.github.akjo03.akjonav.model.map.AkjonavMapBuilder;
import io.github.akjo03.akjonav.model.util.position.AkjonavPositionBuilder;
import io.github.akjo03.util.logging.v2.Logger;
import io.github.akjo03.util.logging.v2.LoggerManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

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
						.build()
		));

		exit(0);
	}

	public void exit(int exitCode) {
		LOGGER.info("Exiting " + AkjonavModelConstants.APP_NAME + " with status code " + exitCode + "...");
		System.exit(SpringApplication.exit(applicationContext, () -> exitCode));
		throw new IllegalStateException("SpringApplication.exit() should have exited the application!");
	}
}