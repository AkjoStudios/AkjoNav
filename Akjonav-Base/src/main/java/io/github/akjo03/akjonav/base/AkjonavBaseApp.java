package io.github.akjo03.akjonav.base;

import io.github.akjo03.akjonav.base.constants.AkjonavBaseConstants;
import io.github.akjo03.akjonav.base.services.JsonService;
import io.github.akjo03.util.logging.v2.Logger;
import io.github.akjo03.util.logging.v2.LoggerManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@RequiredArgsConstructor
@ConfigurationPropertiesScan
public class AkjonavBaseApp {
    private static final Logger LOGGER = LoggerManager.getLogger(AkjonavBaseApp.class)
            .setMinimumLoggingLevel(AkjonavBaseConstants.LOGGING_LEVEL)
            .setLoggingFormat(AkjonavBaseConstants.LOGGING_FORMAT);

    private static final JsonService jsonService = new JsonService();

    public static void main(String[] args) {
        SpringApplication.run(AkjonavBaseApp.class, args);
    }
}