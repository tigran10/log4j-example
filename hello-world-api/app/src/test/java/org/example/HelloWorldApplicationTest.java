package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(OutputCaptureExtension.class)
public class HelloWorldApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testHelloEndpoint(CapturedOutput output) throws Exception {
        this.mockMvc.perform(get("/hello")
                        .header("X-Trace-Token", "trace-12345")
                        .header("X-Correlation-Id", "corr-67890")
                        .header("X-CUSTOM-HEADER", "customValue"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, World!"));


        assertThat(output).contains(
                "[trace-12345]",
                "[unknown-X-User-Id]",
                "[customValue]",
                "[corr-67890]",
                "Hello, World!"
        );
    }

}
