package com.verizon.trainingdemo.ecomeurekaservicediscovery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest
class EcomEurekaServicediscoveryApplicationTests {


	@Autowired
	Environment environment;

	@Test
	public void portTest(){

		String port = "8761";
		Assertions.assertEquals(port, environment.getProperty("server.port"));

	}

	@Test
	public void appNameTest(){

		String name = "ecom-eureka-servicediscovery";
		Assertions.assertEquals(name, environment.getProperty("spring.application.name"));

	}


}
