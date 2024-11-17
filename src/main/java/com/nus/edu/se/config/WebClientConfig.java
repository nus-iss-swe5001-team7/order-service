package com.nus.edu.se.config;

import io.micrometer.core.instrument.MeterRegistry;
//import io.micrometer.core.instrument.binder.http.MetricsWebClientFilterFunction;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }

//    @Bean
//    @LoadBalanced
//    public WebClient.Builder webClientBuilder(MeterRegistry meterRegistry) {
//        return WebClient.builder()
//                .filter(new MetricsWebClientFilterFunction(meterRegistry, "http.client.requests"));
//    }
}
