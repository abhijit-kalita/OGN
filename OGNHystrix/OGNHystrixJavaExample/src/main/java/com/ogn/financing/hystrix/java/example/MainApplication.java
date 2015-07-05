package com.ogn.financing.hystrix.java.example;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableAutoConfiguration
@ComponentScan
public class MainApplication {

	
	
	public static void main(String[] args) {

		//SpringApplication.run(MainApplication.class, args);
		
		new SpringApplicationBuilder(MainApplication.class).properties(
				"spring.config.name:simple").run(args);
	}

}
