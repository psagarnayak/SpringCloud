package com.demo.spring.cloud.order;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OrderAppConfiguration {
	
	@Bean
	@LoadBalanced
	public RestTemplate buildRestTemplate() {
		return new RestTemplate();
	}

}
