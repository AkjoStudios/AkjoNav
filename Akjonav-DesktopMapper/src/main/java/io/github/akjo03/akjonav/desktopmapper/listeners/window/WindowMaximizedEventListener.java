package io.github.akjo03.akjonav.desktopmapper.listeners.window;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.akjo03.akjonav.desktopmapper.AkjonavDesktopMapperApp;
import io.github.akjo03.akjonav.desktopmapper.constants.AkjonavDesktopMapperConstants;
import io.github.akjo03.akjonav.desktopmapper.managers.DataManager;
import io.github.akjo03.akjonav.desktopmapper.services.JsonService;
import io.github.akjo03.akjonav.desktopmapper.services.WindowService;
import io.github.akjo03.util.logging.v2.Logger;
import io.github.akjo03.util.logging.v2.LoggerManager;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

@RequiredArgsConstructor
public class WindowMaximizedEventListener extends WindowAdapter {
	private static final Logger LOGGER = LoggerManager.getLogger(WindowMaximizedEventListener.class)
			.setMinimumLoggingLevel(AkjonavDesktopMapperConstants.LOGGING_LEVEL)
			.setLoggingFormat(AkjonavDesktopMapperConstants.LOGGING_FORMAT);

	private final Window window;

	@Override
	public void windowStateChanged(@NotNull WindowEvent e) {
		LOGGER.debug("Event 'windowStateChanged' triggered");
		if (!e.getComponent().equals(window)) {
			return;
		}
		if (WindowService.isMaximized(e.getNewState()) && !WindowService.isMaximized(e.getOldState())) {
			onWindowMaximized(e.getWindow());
		}
	}

	private void onWindowMaximized(@NotNull Window window) {
		AkjonavDesktopMapperApp.getAppSettings().getWindowSettings().setMaximized("true");
		try {
			DataManager.save("settings.json", JsonService.getObjectMapper().convertValue(AkjonavDesktopMapperApp.getAppSettings(), JsonNode.class));
			LOGGER.debug("Saved settings after maximizing window!");
		} catch (IOException e) {
			LOGGER.error("Failed to save settings after maximizing window!", e);
		}
	}
}
