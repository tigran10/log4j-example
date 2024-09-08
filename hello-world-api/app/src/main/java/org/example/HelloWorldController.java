package org.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
class HelloWorldController {

    @GetMapping("/hello")
    public String hello() {
        log.info("Hello, World!");
        return "Hello, World!";
    }
}
