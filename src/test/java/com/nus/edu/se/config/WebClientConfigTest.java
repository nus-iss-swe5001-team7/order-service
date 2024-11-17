package com.nus.edu.se.config;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = WebClientConfig.class)
class WebClientConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Test
    void testWebClientBuilderBeanExists() {
        assertNotNull(webClientBuilder, "The WebClient.Builder bean should not be null");
    }

    @Test
    void testWebClientBuilderIsLoadBalanced() {
        // Verify if the bean is load-balanced
        String[] beanNames = applicationContext.getBeanNamesForType(WebClient.Builder.class);
        assertTrue(beanNames.length > 0, "There should be at least one WebClient.Builder bean");

        boolean isLoadBalanced = applicationContext.findAnnotationOnBean(beanNames[0], LoadBalanced.class) != null;
        assertTrue(isLoadBalanced, "The WebClient.Builder bean should be annotated with @LoadBalanced");
    }
}