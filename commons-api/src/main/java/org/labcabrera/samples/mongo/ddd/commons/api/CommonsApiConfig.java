package org.labcabrera.samples.mongo.ddd.commons.api;

import org.labcabrera.samples.mongo.ddd.commons.api.errors.DefaultRestExceptionHandler;
import org.labcabrera.samples.mongo.ddd.commons.controller.impl.SwaggerController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SwaggerConfig.class)
public class CommonsApiConfig {

	@Bean
	@ConditionalOnMissingBean
	SwaggerController homeController() {
		return new SwaggerController();
	}

	@Bean
	@ConditionalOnMissingBean
	DefaultRestExceptionHandler defaultRestExceptionHandler() {
		return new DefaultRestExceptionHandler();
	}

}
