package com.nus.edu.se.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/trace-test")
    public String traceTest() {
        return "Trace test successful!";
    }
}
