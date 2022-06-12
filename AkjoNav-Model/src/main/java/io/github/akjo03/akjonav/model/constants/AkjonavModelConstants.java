package io.github.akjo03.akjonav.model.constants;

import io.github.akjo03.util.logging.v2.LoggingLevel;
import lombok.experimental.UtilityClass;

import java.nio.file.Path;

@UtilityClass
@SuppressWarnings("unused")
public class AkjonavModelConstants {
	public final String MODULE_NAME = "AkjoNav-Model";
	public final String APP_NAME = "AkjonavModelApp";
	public final String APP_VERSION = "2022.1-dev1";

	public final boolean DEBUG_MODE = true;
	public final LoggingLevel LOGGING_LEVEL = DEBUG_MODE ? LoggingLevel.DEBUG : LoggingLevel.INFO;
	public final String LOGGING_FORMAT = "[%t] %s[" + MODULE_NAME + "/" + APP_NAME + " @ %i] [%l]: %m";

	public final Path MAPS_FOLDER = Path.of("maps");
}