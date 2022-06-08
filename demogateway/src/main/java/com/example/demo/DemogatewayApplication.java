package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
//@EnableEurekaServer        // It acts as a eureka client
@EnableZuulProxy        // Enable Zuul
@EnableEurekaClient
public class DemogatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemogatewayApplication.class, args);
    }

}
