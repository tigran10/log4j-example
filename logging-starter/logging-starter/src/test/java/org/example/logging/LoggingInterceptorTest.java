package org.example.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoggingInterceptorTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    private LoggingProperties loggingProperties;
    private LoggingInterceptor loggingInterceptor;

    @BeforeEach
    void setUp() {
        loggingProperties = new LoggingProperties();
        loggingProperties.setEnabled(true);
        loggingProperties.setFields(List.of("X-Trace-Token", "X-User-Id"));

        loggingInterceptor = new LoggingInterceptor(loggingProperties);
    }

    @Test
    void testPreHandleAddsMdcValues() throws Exception {
        when(request.getHeader("X-Trace-Token")).thenReturn("test-trace");
        when(request.getHeader("X-User-Id")).thenReturn("test-user");

        loggingInterceptor.preHandle(request, response, new Object());

        assertEquals("test-trace", MDC.get("X-Trace-Token"));
        assertEquals("test-user", MDC.get("X-User-Id"));

        MDC.clear();
    }

    @Test
    void testPreHandleHandlesMissingHeaders() throws Exception {
        when(request.getHeader("X-Trace-Token")).thenReturn(null);
        when(request.getHeader("X-User-Id")).thenReturn(null);

        loggingInterceptor.preHandle(request, response, new Object());

        assertEquals("unknown-X-Trace-Token", MDC.get("X-Trace-Token"));
        assertEquals("unknown-X-User-Id", MDC.get("X-User-Id"));

        MDC.clear();
    }

    @Test
    void testAfterCompletionClearsMdcValues() throws Exception {
        MDC.put("X-Trace-Token", "test-trace");
        MDC.put("X-User-Id", "test-user");

        loggingInterceptor.afterCompletion(request, response, new Object(), null);

        assertNull(MDC.get("X-Trace-Token"));
        assertNull(MDC.get("X-User-Id"));
    }
}