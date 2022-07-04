package io.github.akjo03.akjonav.desktopmapper;

import io.github.akjo03.akjonav.desktopmapper.constants.AkjonavDesktopMapperConstants;
import io.github.akjo03.util.logging.v2.Logger;
import io.github.akjo03.util.logging.v2.LoggerManager;
import javafx.application.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import javax.swing.*;

@SpringBootApplication
@RequiredArgsConstructor
@ConfigurationPropertiesScan
public class AkjonavDesktopMapperApp {
	private static final Logger LOGGER = LoggerManager.getLogger(AkjonavDesktopMapperApp.class)
			.setMinimumLoggingLevel(AkjonavDesktopMapperConstants.LOGGING_LEVEL)
			.setLoggingFormat(AkjonavDesktopMapperConstants.LOGGING_FORMAT);

	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		Application.launch(AkjonavDesktopMapperFxApp.class, args);
	}
}