package org.example.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private final LoggingProperties loggingProperties;

    public LoggingInterceptor(LoggingProperties loggingProperties) {
        this.loggingProperties = loggingProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!loggingProperties.isEnabled()) {
            return true;
        }

        List<String> fields = loggingProperties.getFields();
        if (fields != null) {
            for (String field : fields) {
                String headerValue = request.getHeader(field);
                MDC.put(field, headerValue != null ? headerValue : "unknown-" + field);
            }
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
    }
}
