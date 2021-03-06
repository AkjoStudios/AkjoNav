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
public class WindowRestoredEventListener extends WindowAdapter {
	private static final Logger LOGGER = LoggerManager.getLogger(WindowRestoredEventListener.class)
			.setMinimumLoggingLevel(AkjonavDesktopMapperConstants.LOGGING_LEVEL)
			.setLoggingFormat(AkjonavDesktopMapperConstants.LOGGING_FORMAT);

	private final Window window;

	@Override
	public void windowStateChanged(@NotNull WindowEvent e) {
		LOGGER.debug("Event 'windowStateChanged' triggered");
		if (!e.getComponent().equals(window)) {
			return;
		}
		if (!WindowService.isMaximized(e.getNewState()) && WindowService.isMaximized(e.getOldState())) {
			onWindowRestored(e.getWindow());
		}
	}
	
	private void onWindowRestored(@NotNull Window window) {
		AkjonavDesktopMapperApp.getAppSettings().getWindowSettings().setMaximized("false");
		try {
			DataManager.save("settings.json", JsonService.getObjectMapper().convertValue(AkjonavDesktopMapperApp.getAppSettings(), JsonNode.class));
		} catch (IOException e) {
			LOGGER.error("Failed to save settings after restoring window!", e);
		}

		moveIntoBounds(window);
	}

	private void moveIntoBounds(@NotNull Window window) {
		Rectangle bounds = window.getGraphicsConfiguration().getBounds();
		window.setLocation(bounds.x, bounds.y);

		String oldWidth = AkjonavDesktopMapperApp.getAppSettings().getWindowSettings().getWidth();
		String oldHeight = AkjonavDesktopMapperApp.getAppSettings().getWindowSettings().getHeight();
		try {
			int width = Integer.parseInt(oldWidth);
			int height = Integer.parseInt(oldHeight) - 32;
			if (width > bounds.width) {
				width = bounds.width;
			}
			if (height > bounds.height) {
				height = bounds.height;
			}
			window.setSize(width, height);
		} catch (NumberFormatException e) {
			LOGGER.error("Failed to restore window size!", e);
		}
		LOGGER.info("Window restored after maximization!");
	}
}
