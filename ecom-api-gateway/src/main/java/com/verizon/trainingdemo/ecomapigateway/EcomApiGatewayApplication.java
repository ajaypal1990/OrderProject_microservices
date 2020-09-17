package com.verizon.trainingdemo.ecomapigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.verizon.trainingdemo.ecomapigateway.filter.ErrorFilter;
import com.verizon.trainingdemo.ecomapigateway.filter.GatewayExceptionHandler;
import com.verizon.trainingdemo.ecomapigateway.filter.PostFilter;
import com.verizon.trainingdemo.ecomapigateway.filter.PreFilter;
import com.verizon.trainingdemo.ecomapigateway.filter.RouteFilter;
import com.verizon.trainingdemo.ecomapigateway.security.Configuration;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class EcomApiGatewayApplication {

	private static  final  Logger logger = LoggerFactory.getLogger(EcomApiGatewayApplication.class);

	public static void main(String[] args) {
		logger.debug("*********starting server**********");
		ApplicationContext run = SpringApplication.run(EcomApiGatewayApplication.class, args);
		Configuration bean = run.getBean(Configuration.class);
		System.out.println(bean.getSecretkey());
	}

	@Bean
	public PreFilter preFilter() {
		return new PreFilter();
	}
	@Bean
	public PostFilter postFilter() {
		return new PostFilter();
	}
	@Bean
	public ErrorFilter errorFilter() {
		return new ErrorFilter();
	}
	@Bean
	public RouteFilter routeFilter() {
		return new RouteFilter();
	}

	@Bean
	public GatewayExceptionHandler getGatewayExceptionHandler(){
		return  new GatewayExceptionHandler();
	}

}
