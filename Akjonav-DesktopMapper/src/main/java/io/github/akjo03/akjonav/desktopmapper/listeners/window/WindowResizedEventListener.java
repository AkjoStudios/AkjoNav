package io.github.akjo03.akjonav.desktopmapper.listeners.window;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.akjo03.akjonav.desktopmapper.AkjonavDesktopMapperApp;
import io.github.akjo03.akjonav.desktopmapper.constants.AkjonavDesktopMapperConstants;
import io.github.akjo03.akjonav.desktopmapper.managers.DataManager;
import io.github.akjo03.akjonav.desktopmapper.services.JsonService;
import io.github.akjo03.util.logging.v2.Logger;
import io.github.akjo03.util.logging.v2.LoggerManager;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;

@RequiredArgsConstructor
public class WindowResizedEventListener extends ComponentAdapter {
	private static final Logger LOGGER = LoggerManager.getLogger(WindowResizedEventListener.class)
			.setMinimumLoggingLevel(AkjonavDesktopMapperConstants.LOGGING_LEVEL)
			.setLoggingFormat(AkjonavDesktopMapperConstants.LOGGING_FORMAT);

	private final Window window;

	@Override
	public void componentResized(@NotNull ComponentEvent e) {
		LOGGER.info("Event 'windowResized' triggered");
		if (!e.getComponent().equals(window)) {
			return;
		}
		onWindowResized(window);
	}

	private void onWindowResized(@NotNull Window window) {
		AkjonavDesktopMapperApp.getAppSettings().getWindowSettings().setWidth(String.valueOf(window.getWidth()));
		AkjonavDesktopMapperApp.getAppSettings().getWindowSettings().setHeight(String.valueOf(window.getHeight()));
		AkjonavDesktopMapperApp.getAppSettings().getWindowSettings().setScreenX(String.valueOf(window.getX()));
		AkjonavDesktopMapperApp.getAppSettings().getWindowSettings().setScreenY(String.valueOf(window.getY()));
		try {
			DataManager.save("settings.json", JsonService.getObjectMapper().convertValue(AkjonavDesktopMapperApp.getAppSettings(), JsonNode.class));
		} catch (IOException e) {
			LOGGER.error("Failed to save settings after resizing window!", e);
		}
	}
}