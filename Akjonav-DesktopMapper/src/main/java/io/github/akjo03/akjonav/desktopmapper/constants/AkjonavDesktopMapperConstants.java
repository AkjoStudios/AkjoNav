package io.github.akjo03.akjonav.desktopmapper.constants;

import io.github.akjo03.util.logging.v2.LoggingLevel;
import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class AkjonavDesktopMapperConstants {
	public final String MODULE_NAME = "AkjoNav-DesktopMapper";
	public final String APP_NAME = "AkjonavDesktopMapperApp";
	public final String APP_VERSION = "2022.1-dev1";
	public final String APP_TITLE = "Akjonav DesktopMapper";

	public final boolean DEBUG_MODE = true;
	public final LoggingLevel LOGGING_LEVEL = DEBUG_MODE ? LoggingLevel.DEBUG : LoggingLevel.INFO;
	public final String LOGGING_FORMAT = "[%t] %s[" + MODULE_NAME + "/" + APP_NAME + " @ %i] [%l]: %m";
	public final Pattern LOGGING_PREFIX_PATTERN = Pattern.compile("\\[\\d{4}(-\\d{2}){2} \\d{2}(:\\d{2}){2}\\.\\d{3,4}] \\[[A-Za-z\\d-_]+/[A-Za-z\\d-_]+ @ [A-Za-z\\d-_]+] \\[[A-Z]+]: ");
}