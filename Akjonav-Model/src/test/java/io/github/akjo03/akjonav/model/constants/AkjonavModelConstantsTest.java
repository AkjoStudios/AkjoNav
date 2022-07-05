package io.github.akjo03.akjonav.model.constants;

import io.github.akjo03.util.logging.v2.LoggingLevel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AkjonavModelConstantsTest {
	@Test
	void testModuleName() {
		assertEquals("AkjoNav-Model", AkjonavModelConstants.MODULE_NAME);

	}

	@Test
	void testAppName() {
		assertEquals("AkjonavModelApp", AkjonavModelConstants.APP_NAME);

	}

	@Test
	void testAppVersion() {
		assertEquals("2022.1-dev1", AkjonavModelConstants.APP_VERSION);

	}

	@Test
	void testLoggingLevel() {
		if (AkjonavModelConstants.DEBUG_MODE) {
			assertEquals(LoggingLevel.DEBUG, AkjonavModelConstants.LOGGING_LEVEL);
		} else {
			assertEquals(LoggingLevel.INFO, AkjonavModelConstants.LOGGING_LEVEL);
		}
	}

	@Test
	void testLoggingFormat() {
		assertEquals("[%t] %s[" + AkjonavModelConstants.MODULE_NAME + "/" + AkjonavModelConstants.APP_NAME + " @ %i] [%l]: %m", AkjonavModelConstants.LOGGING_FORMAT);
	}

	@Test
	void testLoggingPrefixPattern() {
		assertEquals("\\[\\d{4}(-\\d{2}){2} \\d{2}(:\\d{2}){2}\\.\\d{3,4}] \\[[A-Za-z\\d-_]+/[A-Za-z\\d-_]+ @ [A-Za-z\\d-_]+] \\[[A-Z]+]: ", AkjonavModelConstants.LOGGING_PREFIX_PATTERN.pattern());
	}

	@Test
	void testLoggingPrefixPatternValidMatch() {
		assertTrue(AkjonavModelConstants.LOGGING_PREFIX_PATTERN.matcher("[2020-01-01 00:00:00.000] [AkjoNav-Model/AkjonavModelApp @ AkjonavPositionBuilder] [INFO]: ").matches());
		assertTrue(AkjonavModelConstants.LOGGING_PREFIX_PATTERN.matcher("[2020-01-01 00:00:00.0000] [AkjoNav-TestModule/AkjonavTestApp @ AkjonavTester] [DEBUG]: ").matches());
		assertTrue(AkjonavModelConstants.LOGGING_PREFIX_PATTERN.matcher("[2020-01-01 00:00:00.0000] [AkjoNav-TestModule/AkjonavTestApp @ AkjonavTester] [ERROR]: ").matches());
	}

	@Test
	void testLoggingPrefixPatternInvalidMatch() {
		assertFalse(AkjonavModelConstants.LOGGING_PREFIX_PATTERN.matcher("[2020-01-01 00:00:00.000] [AkjoNav-Model/AkjonavModelApp AkjonavPositionBuilder] [INFO]: ").matches());
		assertFalse(AkjonavModelConstants.LOGGING_PREFIX_PATTERN.matcher("[2020-01-01 00:00:0] [AkjoNav-TestModule/AkjonavTestApp @ AkjonavTester] []: ").matches());
		assertFalse(AkjonavModelConstants.LOGGING_PREFIX_PATTERN.matcher("[00:00:00.0000] [AkjonavTestApp @ AkjonavTester] [DEBUG]: ").matches());
	}

	@Test
	void testLoggingPrefixPatternReplace() {
		assertEquals("Test Message", AkjonavModelConstants.LOGGING_PREFIX_PATTERN.matcher("[2020-01-01 00:00:00.000] [AkjoNav-Model/AkjonavModelApp @ AkjonavPositionBuilder] [INFO]: Test Message").replaceAll(""));
	}

	@Test
	void testMapsFolder() {
		assertEquals("maps", AkjonavModelConstants.MAPS_FOLDER.toString());
	}
}