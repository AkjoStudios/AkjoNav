package io.github.akjo03.akjonav.desktopmapper;

import io.github.akjo03.akjonav.desktopmapper.constants.AkjonavDesktopMapperConstants;
import io.github.akjo03.akjonav.desktopmapper.listeners.window.WindowMovedEventListener;
import io.github.akjo03.akjonav.desktopmapper.listeners.window.WindowResizedEventListener;
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

		setInitialPosition();
		setInitialSize();

		initializeListeners();
	}

	private void setInitialPosition() {
		String screenX = AkjonavDesktopMapperApp.getAppSettings().getWindowSettings().getScreenX();
		String screenY = AkjonavDesktopMapperApp.getAppSettings().getWindowSettings().getScreenY();
		if (screenX.equals("null") || screenY.equals("null")) {
			setLocationRelativeTo(null);
		} else {
			try {
				int screenXInt = Integer.parseInt(screenX);
				int screenYInt = Integer.parseInt(screenY);
				if (screenXInt > 0 && screenYInt > 0) {
					setLocation(screenXInt, screenYInt);
				} else {
					setLocation(0, 0);
				}
			} catch (NumberFormatException e) {
				LOGGER.warn("Failed to parse window position settings, using default values.");
				setLocationRelativeTo(null);
			}
		}
	}

	private void setInitialSize() {
		setMinimumSize(new Dimension(600, 300));
		if (Boolean.parseBoolean(AkjonavDesktopMapperApp.getAppSettings().getWindowSettings().getMaximized())) {
			setExtendedState(Frame.MAXIMIZED_BOTH);
		} else {
			String width = AkjonavDesktopMapperApp.getAppSettings().getWindowSettings().getWidth();
			String height = AkjonavDesktopMapperApp.getAppSettings().getWindowSettings().getHeight();
			try {
				int widthInt = Integer.parseInt(width);
				int heightInt = Integer.parseInt(height);
				setSize(new Dimension(widthInt, heightInt));
			} catch (NumberFormatException e) {
				LOGGER.warn("Failed to parse window size settings, using default values.");
				setSize(new Dimension(800, 600));
			}
		}
	}

	private void initializeListeners() {
		LOGGER.info("Initializing listeners...");
		addComponentListener(new WindowMovedEventListener(this));
		addComponentListener(new WindowResizedEventListener(this));
		LOGGER.info("Successfully initialized all listeners.");
	}
}