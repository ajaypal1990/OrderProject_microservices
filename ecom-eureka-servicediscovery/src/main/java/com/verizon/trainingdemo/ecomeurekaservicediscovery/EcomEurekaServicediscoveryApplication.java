package com.verizon.trainingdemo.ecomeurekaservicediscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EcomEurekaServicediscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcomEurekaServicediscoveryApplication.class, args);
	}

}
