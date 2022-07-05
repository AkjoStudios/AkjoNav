package io.github.akjo03.akjonav.base;

import io.github.akjo03.akjonav.base.constants.AkjonavBaseConstants;
import io.github.akjo03.akjonav.base.services.JsonService;
import io.github.akjo03.akjonav.model.util.position.AkjonavPosition;
import io.github.akjo03.akjonav.model.util.position.AkjonavPositionBuilder;
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
        AkjonavPosition startPosition = new AkjonavPositionBuilder(46.8938422, 7.3298003).build();
        AkjonavPosition endPosition = new AkjonavPositionBuilder(46.8937608, 7.3298296).build();

        System.out.println(startPosition.serialize(jsonService.getObjectMapper()));
        System.out.println(endPosition.serialize(jsonService.getObjectMapper()));
    }
}