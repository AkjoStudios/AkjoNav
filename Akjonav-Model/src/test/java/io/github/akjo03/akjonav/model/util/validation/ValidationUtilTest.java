package io.github.akjo03.akjonav.model.util.validation;

import io.github.akjo03.akjonav.model.constants.AkjonavModelConstants;
import io.github.akjo03.util.logging.LoggingLevel;
import io.github.akjo03.util.logging.v2.Logger;
import io.github.akjo03.util.logging.v2.LoggerManager;
import io.validly.Notification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static io.validly.NoteFirstValidator.valid;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ValidationUtilTest {
	private final PrintStream originalOut = System.out;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@BeforeEach
	void prepareOutStream() {
		System.setOut(new PrintStream(outContent));
	}

	@Test
	void testPrintValidValidationWithName() {
		Notification notification = new Notification();
		String testData = "testData";
		valid(testData, "testData", notification)
				.must(data -> data.equals(testData), "TestData must be equal to testData!");
		Logger logger = LoggerManager.getLogger(ValidationUtilTest.class, AkjonavModelConstants.LOGGING_LEVEL);
		logger.setLoggingFormat(AkjonavModelConstants.LOGGING_FORMAT);
		ValidationUtil.printValidationReport("ValidationUtilTest", notification, logger);

		String[] outLines = outContent.toString().split("\n");
		outLines = Arrays.stream(outLines)
				.map(line -> line.replaceAll(AkjonavModelConstants.LOGGING_PREFIX_PATTERN.pattern(), "").strip())
				.toArray(String[]::new);

		assertArrayEquals(new String[] {
				"---------------------------------------------------------------------------------------",
				"Validation report for ValidationUtilTest:",
				LoggingLevel.SUCCESS.getColor().colorize("\tNo errors found.", false),
				"---------------------------------------------------------------------------------------"
		}, outLines);
	}

	@Test
	void testPrintValidValidationWithClassAndLogger() {
		Notification notification = new Notification();
		String testData = "testData";
		valid(testData, "testData", notification)
				.must(data -> data.equals(testData), "TestData must be equal to testData!");
		Logger logger = LoggerManager.getLogger(ValidationUtilTest.class, AkjonavModelConstants.LOGGING_LEVEL);
		logger.setLoggingFormat(AkjonavModelConstants.LOGGING_FORMAT);
		ValidationUtil.printValidationReport(ValidationUtilTest.class, notification, logger);

		String[] outLines = outContent.toString().split("\n");
		outLines = Arrays.stream(outLines)
				.map(line -> line.replaceAll(AkjonavModelConstants.LOGGING_PREFIX_PATTERN.pattern(), "").strip())
				.toArray(String[]::new);

		assertArrayEquals(new String[] {
				"---------------------------------------------------------------------------------------",
				"Validation report for ValidationUtilTest:",
				LoggingLevel.SUCCESS.getColor().colorize("\tNo errors found.", false),
				"---------------------------------------------------------------------------------------"
		}, outLines);
	}

	@Test
	void testPrintValidValidationWithOnlyClass() {
		Notification notification = new Notification();
		String testData = "testData";
		valid(testData, "testData", notification)
				.must(data -> data.equals(testData), "TestData must be equal to testData!");
		Logger logger = LoggerManager.getLogger(ValidationUtilTest.class, AkjonavModelConstants.LOGGING_LEVEL);
		logger.setLoggingFormat(AkjonavModelConstants.LOGGING_FORMAT);
		ValidationUtil.printValidationReport(ValidationUtilTest.class, notification);

		String[] outLines = outContent.toString().split("\n");
		outLines = Arrays.stream(outLines)
				.map(line -> line.replaceAll(AkjonavModelConstants.LOGGING_PREFIX_PATTERN.pattern(), "").strip())
				.toArray(String[]::new);

		assertArrayEquals(new String[] {
				"---------------------------------------------------------------------------------------",
				"Validation report for ValidationUtilTest:",
				LoggingLevel.SUCCESS.getColor().colorize("\tNo errors found.", false),
				"---------------------------------------------------------------------------------------"
		}, outLines);
	}

	@Test
	void testPrintInvalidValidationWithName() {
		Notification notification = new Notification();
		String testData = "testData";
		valid(testData, "testData", notification)
				.must(data -> !data.equals(testData), "TestData must be equal to testData!");
		Logger logger = LoggerManager.getLogger(ValidationUtilTest.class, AkjonavModelConstants.LOGGING_LEVEL);
		logger.setLoggingFormat(AkjonavModelConstants.LOGGING_FORMAT);
		ValidationUtil.printValidationReport("ValidationUtilTest", notification, logger);

		String[] outLines = outContent.toString().split("\n");
		outLines = Arrays.stream(outLines)
				.map(line -> line.replaceAll(AkjonavModelConstants.LOGGING_PREFIX_PATTERN.pattern(), "").stripTrailing())
				.toArray(String[]::new);

		assertArrayEquals(new String[] {
				"---------------------------------------------------------------------------------------",
				"Validation report for ValidationUtilTest:",
				"\ttestData:",
				LoggingLevel.ERROR.getColor().colorize("\t\tTestData must be equal to testData!", false),
				"---------------------------------------------------------------------------------------"
		}, outLines);
	}

	@Test
	void testPrintInvalidValidationWithClassAndLogger() {
		Notification notification = new Notification();
		String testData = "testData";
		valid(testData, "testData", notification)
				.must(data -> !data.equals(testData), "TestData must be equal to testData!");
		Logger logger = LoggerManager.getLogger(ValidationUtilTest.class, AkjonavModelConstants.LOGGING_LEVEL);
		logger.setLoggingFormat(AkjonavModelConstants.LOGGING_FORMAT);
		ValidationUtil.printValidationReport(ValidationUtilTest.class, notification, logger);

		String[] outLines = outContent.toString().split("\n");
		outLines = Arrays.stream(outLines)
				.map(line -> line.replaceAll(AkjonavModelConstants.LOGGING_PREFIX_PATTERN.pattern(), "").stripTrailing())
				.toArray(String[]::new);

		assertArrayEquals(new String[] {
				"---------------------------------------------------------------------------------------",
				"Validation report for ValidationUtilTest:",
				"\ttestData:",
				LoggingLevel.ERROR.getColor().colorize("\t\tTestData must be equal to testData!", false),
				"---------------------------------------------------------------------------------------"
		}, outLines);
	}

	@Test
	void testPrintInvalidValidationWithOnlyClass() {
		Notification notification = new Notification();
		String testData = "testData";
		valid(testData, "testData", notification)
				.must(data -> !data.equals(testData), "TestData must be equal to testData!");
		Logger logger = LoggerManager.getLogger(ValidationUtilTest.class, AkjonavModelConstants.LOGGING_LEVEL);
		logger.setLoggingFormat(AkjonavModelConstants.LOGGING_FORMAT);
		ValidationUtil.printValidationReport(ValidationUtilTest.class, notification);

		String[] outLines = outContent.toString().split("\n");
		outLines = Arrays.stream(outLines)
				.map(line -> line.replaceAll(AkjonavModelConstants.LOGGING_PREFIX_PATTERN.pattern(), "").stripTrailing())
				.toArray(String[]::new);

		assertArrayEquals(new String[] {
				"---------------------------------------------------------------------------------------",
				"Validation report for ValidationUtilTest:",
				"\ttestData:",
				LoggingLevel.ERROR.getColor().colorize("\t\tTestData must be equal to testData!", false),
				"---------------------------------------------------------------------------------------"
		}, outLines);
	}

	@Test
	void testPrintMultipleInvalidValidationWithName() {
		Notification notification = new Notification();
		String testData = "testData";
		String testData2 = "testData2";
		valid(testData, "testData", notification)
				.must(data -> !data.equals(testData), "TestData must be equal to testData!")
				.must(data -> data.equals(testData2), "TestData must be equal to testData2!");
		valid(testData2, "testData2", notification)
				.must(data -> !data.equals(testData2), "TestData2 must be equal to testData2!")
				.must(data -> data.equals(testData), "TestData2 must be equal to testData!");
		Logger logger = LoggerManager.getLogger(ValidationUtilTest.class, AkjonavModelConstants.LOGGING_LEVEL);
		logger.setLoggingFormat(AkjonavModelConstants.LOGGING_FORMAT);
		ValidationUtil.printValidationReport("ValidationUtilTest", notification, logger);

		String[] outLines = outContent.toString().split("\n");
		outLines = Arrays.stream(outLines)
				.map(line -> line.replaceAll(AkjonavModelConstants.LOGGING_PREFIX_PATTERN.pattern(), "").stripTrailing())
				.toArray(String[]::new);

		assertArrayEquals(new String[] {
				"---------------------------------------------------------------------------------------",
				"Validation report for ValidationUtilTest:",
				"\ttestData:",
				LoggingLevel.ERROR.getColor().colorize("\t\tTestData must be equal to testData!", false),
				"\ttestData2:",
				LoggingLevel.ERROR.getColor().colorize("\t\tTestData2 must be equal to testData2!", false),
				"---------------------------------------------------------------------------------------"
		}, outLines);
	}

	@AfterEach
	void restoreOutStream() {
		System.setOut(originalOut);
	}
}