package io.github.akjo03.akjonav.base;

import io.github.akjo03.akjonav.base.constants.AkjonavBaseConstants;
import io.github.akjo03.util.logging.v2.Logger;
import io.github.akjo03.util.logging.v2.LoggerManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@RequiredArgsConstructor
public class AkjonavBaseApp {
    private static final Logger LOGGER = LoggerManager.getLogger(AkjonavBaseApp.class, AkjonavBaseConstants.LOGGING_LEVEL);

    private final ApplicationContext applicationContext;

    public static void main(String[] args) {
        LOGGER.setLoggingFormat(AkjonavBaseConstants.LOGGING_FORMAT);
        SpringApplication.run(AkjonavBaseApp.class, args);
    }
}