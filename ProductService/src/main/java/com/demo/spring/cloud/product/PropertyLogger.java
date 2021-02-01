package com.demo.spring.cloud.product;

import java.util.Arrays;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.StandardServletEnvironment;

@Component
public class PropertyLogger {

	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyLogger.class);

	@EventListener
	public void handleContextRefresh(ContextRefreshedEvent event) {

		final ConfigurableEnvironment env = (ConfigurableEnvironment) event.getApplicationContext().getEnvironment();
		printApplicationProperties(env);
	}

	private void printApplicationProperties(ConfigurableEnvironment env) {

		LOGGER.info("====== Environment and configuration ======");
		LOGGER.info("Active profiles: {}", Arrays.toString(env.getActiveProfiles()));
		if (env instanceof StandardServletEnvironment) {
			StandardServletEnvironment sEnv = (StandardServletEnvironment) env;

			final MutablePropertySources sources = sEnv.getPropertySources();

			StreamSupport.stream(sources.spliterator(), false).filter(ps -> ps instanceof EnumerablePropertySource)
					.map(ps -> (EnumerablePropertySource<?>) ps)
					.filter(p -> p.getName().contains("bootstrapProperties")
							|| p.getName().contains("applicationConfig"))
					.map(p -> p.getPropertyNames()).flatMap(Arrays::stream).distinct()
					.filter(prop -> !(prop.contains("credentials") || prop.contains("password"))).sorted()
					.forEach(prop -> LOGGER.info("{}: {}", prop, env.getProperty(prop)));
		}
		LOGGER.info("===========================================");

	}

}