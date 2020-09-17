package com.verizon.trainingdemo.ecomapi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest
class EcomConfigServerApplicationTests {


	@Autowired
	Environment environment;

	@Test
	public void portTest(){

		String port = "8888";
		Assertions.assertEquals(port, environment.getProperty("server.port"));

	}

	@Test
	public void appNameTest(){

		String name = "ecom-configserver";
		Assertions.assertEquals(name, environment.getProperty("spring.application.name"));

	}



}
