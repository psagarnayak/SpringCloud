package com.demo.spring.cloud.order;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OrderAppConfiguration {
	
	@Bean
	public RestTemplate buildRestTemplate() {
		return new RestTemplate();
	}

}
