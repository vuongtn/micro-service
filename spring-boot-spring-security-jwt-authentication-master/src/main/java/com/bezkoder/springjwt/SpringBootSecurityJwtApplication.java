package com.bezkoder.springjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient

public class SpringBootSecurityJwtApplication {

	public static void main(String[] args) {
    SpringApplication.run(SpringBootSecurityJwtApplication.class, args);
	}

}
