package io.github.akjo03.akjonav.desktopmapper;

import io.github.akjo03.akjonav.desktopmapper.constants.AkjonavDesktopMapperConstants;
import io.github.akjo03.akjonav.desktopmapper.domain.AppSettings;
import io.github.akjo03.akjonav.desktopmapper.managers.DataManager;
import io.github.akjo03.akjonav.desktopmapper.managers.ThemeManager;
import io.github.akjo03.akjonav.desktopmapper.services.JsonService;
import io.github.akjo03.util.logging.v2.Logger;
import io.github.akjo03.util.logging.v2.LoggerManager;
import lombok.Getter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.io.IOException;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AkjonavDesktopMapperApp {
	private static final Logger LOGGER = LoggerManager.getLogger(AkjonavDesktopMapperApp.class)
			.setMinimumLoggingLevel(AkjonavDesktopMapperConstants.LOGGING_LEVEL)
			.setLoggingFormat(AkjonavDesktopMapperConstants.LOGGING_FORMAT);

	@Getter
	private static ConfigurableApplicationContext applicationContext;

	@Getter
	private static AppSettings appSettings;

	public static void main(String[] args) {
		applicationContext = new SpringApplicationBuilder(AkjonavDesktopMapperApp.class)
				.headless(false)
				.run(args);

		try {
			DataManager.initialize();
			LOGGER.info("Successfully initialized data manager.");
		} catch (IOException e) {
			LOGGER.error("Failed to initialize data manager!", e);
			System.exit(1);
		}
		if (!DataManager.exists("settings.json")) {
			LOGGER.info("No settings file found, creating default settings file...");
			try {
				DataManager.save("settings.json", JsonService.getObjectMapper().readTree(AkjonavDesktopMapperApp.getApplicationContext().getResource("classpath:default_settings.json").getFile()));
				LOGGER.info("Default settings file created successfully!");
			} catch (IOException e) {
				LOGGER.error("Failed to initialize settings!", e);
				System.exit(1);
			}
		}

		try {
			appSettings = JsonService.getObjectMapper().convertValue(DataManager.load("settings.json"), AppSettings.class);
			LOGGER.info("Successfully loaded settings!");
		} catch (IOException e) {
			LOGGER.error("Failed to load settings!", e);
			System.exit(1);
		}

		try {
			UIManager.setLookAndFeel(ThemeManager.loadTheme(appSettings.getWindowSettings().getTheme()));
			LOGGER.info("Theme " + appSettings.getWindowSettings().getTheme() + " successfully loaded.");
		} catch (UnsupportedLookAndFeelException e) {
			LOGGER.error("Failed to load theme because it is not supported!", e);
			System.exit(1);
		} catch (IllegalArgumentException e1) {
			LOGGER.error("Failed to load theme from settings! Using default dark theme...", e1);
			try {
				UIManager.setLookAndFeel(ThemeManager.loadTheme("dark"));
				LOGGER.info("Theme dark successfully loaded.");
			} catch (UnsupportedLookAndFeelException e2) {
				LOGGER.error("Failed to load default theme because it is not supported!", e2);
				System.exit(1);
			}
		}

		LOGGER.info("Initializing window...");
		SwingUtilities.invokeLater(() -> {
			new DesktopMapperAppWindow().setVisible(true);
		});
	}
}