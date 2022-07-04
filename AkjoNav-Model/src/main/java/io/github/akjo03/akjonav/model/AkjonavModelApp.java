package io.github.akjo03.akjonav.model;

import io.github.akjo03.akjonav.model.constants.AkjonavModelConstants;
import io.github.akjo03.util.logging.v2.Logger;
import io.github.akjo03.util.logging.v2.LoggerManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AkjonavModelApp {
	private static final Logger LOGGER = LoggerManager.getLogger(AkjonavModelApp.class, AkjonavModelConstants.LOGGING_LEVEL);

	public static void main(String[] args) {
		LOGGER.setLoggingFormat(AkjonavModelConstants.LOGGING_FORMAT);
	}
}