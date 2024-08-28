package com.nus.edu.se;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
//@EnableDiscoveryClient
//@EnableFeignClients
@EnableEurekaServer
public class GroupFoodOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroupFoodOrderApplication.class, args);
	}

}
