package io.github.akjo03.akjonav.testmapcreator;

import io.github.akjo03.akjonav.testmapcreator.constants.AkjonavTestMapCreatorConstants;
import io.github.akjo03.akjonav.testmapcreator.services.JsonService;
import io.github.akjo03.util.logging.v2.Logger;
import io.github.akjo03.util.logging.v2.LoggerManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

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



		LOGGER.info("Finished TestMapCreator!");
	}
}