package io.github.akjo03.akjonav.model.util.validation;

import io.github.akjo03.akjonav.model.constants.AkjonavModelConstants;
import io.github.akjo03.util.logging.v2.Logger;
import io.github.akjo03.util.logging.v2.LoggerManager;
import io.validly.Notification;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@UtilityClass
public class ValidationUtil {
	public void printValidationReport(@NotNull String reportName, @NotNull Notification notification, @NotNull Logger logger) {
		logger.info("---------------------------------------------------------------------------------------");
		logger.info("Validation report for " + reportName + ":");
		if (notification.isNotEmpty()) {
			notification.getMessages().forEach((key, messages) -> {
				logger.info("\t" + key + ":");
				messages.forEach(message -> logger.error("\t\t" + message));
			});
		} else {
			logger.success("\tNo errors found.");
		}
		logger.info("---------------------------------------------------------------------------------------");
	}

	public void printValidationReport(@NotNull Class<?> reportClass, @NotNull Notification notification, @NotNull Logger logger) {
		printValidationReport(reportClass.getSimpleName(), notification, logger);
	}

	public void printValidationReport(@NotNull Class<?> reportClass, @NotNull Notification notification) {
		Logger logger = LoggerManager.getLogger(reportClass, AkjonavModelConstants.LOGGING_LEVEL);
		logger.setLoggingFormat(AkjonavModelConstants.LOGGING_FORMAT);
		printValidationReport(reportClass.getSimpleName(), notification, logger);
	}

	public void onError(@NotNull Notification notification, @NotNull Consumer<Notification> consumer) {
		if (notification.isNotEmpty()) {
			consumer.accept(notification);
		}
	}
}