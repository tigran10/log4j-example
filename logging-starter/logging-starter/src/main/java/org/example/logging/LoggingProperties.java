package org.example.logging;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "logging.mdc")
public class LoggingProperties {
    private boolean enabled = true;
    private List<String> fields;
}
