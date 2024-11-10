package com.nus.edu.se;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableEurekaServer
@EnableScheduling
public class GroupFoodOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroupFoodOrderApplication.class, args);
	}

}
