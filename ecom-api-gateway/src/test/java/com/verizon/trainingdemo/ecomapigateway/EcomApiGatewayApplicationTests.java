package com.verizon.trainingdemo.ecomapigateway;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootTest
class EcomApiGatewayApplicationTests extends WebSecurityConfigurerAdapter {


	@Autowired
	Environment environment;

	@Test
	public void portTest(){

		String port = "8080";
		Assertions.assertEquals(port, environment.getProperty("server.port"));

	}

	@Test
	public void appNameTest(){

		String name = "ecom-apigateway";
		Assertions.assertEquals(name, environment.getProperty("spring.application.name"));

	}

	@Test
	public void eurekaURLTest(){
		String url =  "http://localhost:8761/eureka";
		Assertions.assertEquals(url, environment.getProperty("eureka.client.serviceUrl.defaultZone"));
	}




}
