package io.github.akjo03.akjonav.desktopmapper;

import io.github.akjo03.akjonav.desktopmapper.constants.AkjonavDesktopMapperConstants;
import io.github.akjo03.akjonav.desktopmapper.listeners.window.WindowMaximizedEventListener;
import io.github.akjo03.akjonav.desktopmapper.listeners.window.WindowMovedEventListener;
import io.github.akjo03.akjonav.desktopmapper.listeners.window.WindowResizedEventListener;
import io.github.akjo03.akjonav.desktopmapper.listeners.window.WindowRestoredEventListener;
import io.github.akjo03.util.logging.v2.Logger;
import io.github.akjo03.util.logging.v2.LoggerManager;

import javax.swing.*;
import java.awt.*;

public class DesktopMapperAppWindow extends JFrame {
	private static final Logger LOGGER = LoggerManager.getLogger(DesktopMapperAppWindow.class)
			.setMinimumLoggingLevel(AkjonavDesktopMapperConstants.LOGGING_LEVEL)
			.setLoggingFormat(AkjonavDesktopMapperConstants.LOGGING_FORMAT);

	protected DesktopMapperAppWindow() {
		super(AkjonavDesktopMapperConstants.APP_TITLE);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		setInitialSize();
		setInitialPosition();

		initializeListeners();
	}

	private void setInitialSize() {
		setMinimumSize(new Dimension(600, 300));
		if (Boolean.parseBoolean(AkjonavDesktopMapperApp.getAppSettings().getWindowSettings().getMaximized())) {
			setExtendedState(Frame.MAXIMIZED_BOTH);
		} else {
			String width = AkjonavDesktopMapperApp.getAppSettings().getWindowSettings().getWidth();
			String height = AkjonavDesktopMapperApp.getAppSettings().getWindowSettings().getHeight();
			LOGGER.info("Initializing window size to " + width + "x" + height);
			try {
				int widthInt = Integer.parseInt(width);
				int heightInt = Integer.parseInt(height);
				setSize(new Dimension(widthInt, heightInt));
			} catch (NumberFormatException e) {
				LOGGER.warn("Failed to parse window size settings, using default values.");
				setSize(new Dimension(800, 600));
			}
		}
		LOGGER.info("Successfully initialized window size to " + getWidth() + "x" + getHeight() + "!");
	}

	private void setInitialPosition() {
		String screenX = AkjonavDesktopMapperApp.getAppSettings().getWindowSettings().getScreenX();
		String screenY = AkjonavDesktopMapperApp.getAppSettings().getWindowSettings().getScreenY();
		if (screenX.equals("null") || screenY.equals("null")) {
			centerWindow();
			LOGGER.info("No saved window position found, using default centered position at " + getX() + "x" + getY() + "!");
		} else {
			LOGGER.info("Initializing window position to " + screenX + "x" + screenY + "!");
			try {
				int screenXInt = Integer.parseInt(screenX);
				int screenYInt = Integer.parseInt(screenY);
				setLocation(screenXInt, screenYInt);
			} catch (NumberFormatException e) {
				centerWindow();
				LOGGER.info("No saved window position found, using default centered position at " + getX() + "x" + getY() + "!");
			}
		}
		LOGGER.info("Successfully initialized window position at " + getX() + "x" + getY() + "!");
	}

	private void initializeListeners() {
		LOGGER.info("Initializing listeners...");
		addComponentListener(new WindowMovedEventListener(this));
		addComponentListener(new WindowResizedEventListener(this));
		addWindowStateListener(new WindowMaximizedEventListener(this));
		addWindowStateListener(new WindowRestoredEventListener(this));
		LOGGER.info("Successfully initialized all listeners.");
	}

	private void centerWindow() {
		setLocationRelativeTo(null);
	}
}