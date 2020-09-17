package com.verizon.trainingdemo.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("user")
public class Configuration {
	
	public Configuration() {
		System.out.println("Bean for configuration created");
	}
	
	@Value("${user.sercetkey}")
	private String secretkey;

	public String getSecretkey() {
		return secretkey;
	}

	public void setSecretkey(String secretkey) {
		this.secretkey = secretkey;
	}


}
