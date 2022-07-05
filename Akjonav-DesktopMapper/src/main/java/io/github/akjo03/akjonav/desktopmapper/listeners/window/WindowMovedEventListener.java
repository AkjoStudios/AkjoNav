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
public class WindowMovedEventListener extends ComponentAdapter {
	private static final Logger LOGGER = LoggerManager.getLogger(WindowMovedEventListener.class)
			.setMinimumLoggingLevel(AkjonavDesktopMapperConstants.LOGGING_LEVEL)
			.setLoggingFormat(AkjonavDesktopMapperConstants.LOGGING_FORMAT);

	private final Window window;

	@Override
	public void componentMoved(@NotNull ComponentEvent e) {
		LOGGER.debug("Event 'windowMoved' triggered");
		if (!e.getComponent().equals(window)) {
			return;
		}
		onWindowMoved(window);
	}

	private void onWindowMoved(@NotNull Window window) {
		AkjonavDesktopMapperApp.getAppSettings().getWindowSettings().setScreenX(String.valueOf(window.getX()));
		AkjonavDesktopMapperApp.getAppSettings().getWindowSettings().setScreenY(String.valueOf(window.getY()));
		try {
			DataManager.save("settings.json", JsonService.getObjectMapper().convertValue(AkjonavDesktopMapperApp.getAppSettings(), JsonNode.class));
			LOGGER.debug("Saved settings after moving window to " + window.getX() + "x" + window.getY() + "!");
		} catch (IOException e) {
			LOGGER.error("Failed to save settings after moving window!", e);
		}
	}
}