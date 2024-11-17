package com.nus.edu.se.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TestController.class)
class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testTraceTestEndpoint() throws Exception {
        // Perform GET request to /trace-test
        mockMvc.perform(get("/trace-test"))
                // Assert the HTTP status is 200 (OK)
                .andExpect(status().isOk())
                // Assert the response content is as expected
                .andExpect(content().string("Trace test successful!"));
    }
}
