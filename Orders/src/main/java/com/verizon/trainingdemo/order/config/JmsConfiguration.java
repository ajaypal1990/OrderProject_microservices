package com.verizon.trainingdemo.order.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.stereotype.Component;


@Component
public class JmsConfiguration {

	

	@Value("${activemq.broker.url}")
	public String brokerUrl;

	@Value("${activemq.broker.username}")
	public String userName;

	@Value("${activemq.broker.password}")
	String password;

	@Bean
	public JmsListenerContainerFactory<?> myFactory(
			ConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		
		
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setPubSubDomain(true);
		configurer.configure(factory, connectionFactory);
		return factory;
	}
	
	 @Bean
	   public ConnectionFactory connectionFactory(){
	        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
	        connectionFactory.setBrokerURL(brokerUrl);
	        connectionFactory.setUserName(userName);
	        connectionFactory.setPassword(password);
	        return connectionFactory;
	    }
	

	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}
	
	@Bean
	public JmsTemplate jmsTemplate(){
	    JmsTemplate template = new JmsTemplate();
	    template.setConnectionFactory(connectionFactory());
	    template.setMessageConverter(jacksonJmsMessageConverter());
	    template.setPubSubDomain(true);
	    return template;
	}

}
