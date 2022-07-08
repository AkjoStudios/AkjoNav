package io.github.akjo03.akjonav.testmapcreator;

import io.github.akjo03.akjonav.testmapcreator.constants.AkjonavTestMapCreatorConstants;
import io.github.akjo03.akjonav.testmapcreator.services.JsonService;
import io.github.akjo03.util.logging.v2.Logger;
import io.github.akjo03.util.logging.v2.LoggerManager;
import io.github.akjo03.util.math.unit.units.speed.SpeedUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.EnumSet;

@SpringBootApplication
@RequiredArgsConstructor
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

		EnumSet<SpeedUnit> speedUnits = EnumSet.allOf(SpeedUnit.class);
		speedUnits.remove(SpeedUnit.KILOMETRES_PER_HOUR);
		System.out.println(speedUnits);

		LOGGER.info("Finished TestMapCreator!");
	}
}