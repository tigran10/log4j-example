package org.example.logging;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LoggingPropertiesTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(LoggingPropertiesTest.Config.class);

    @Test
    void testLoggingPropertiesAreLoaded() {
        this.contextRunner.run(context -> {
            LoggingProperties properties = context.getBean(LoggingProperties.class);
            assertThat(properties.isEnabled()).isTrue();
            assertThat(properties.getFields()).containsExactly("X-Trace-Token", "X-User-Id");
        });
    }

    @Configuration
    static class Config {
        @Bean
        public LoggingProperties loggingProperties() {
            LoggingProperties properties = new LoggingProperties();
            properties.setEnabled(true);
            properties.setFields(List.of("X-Trace-Token", "X-User-Id"));
            return properties;
        }
    }
}