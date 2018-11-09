package org.labcabrera.samples.mongo.ddd.commons.api;

import org.labcabrera.samples.mongo.ddd.commons.api.controller.impl.SwaggerController;
import org.labcabrera.samples.mongo.ddd.commons.api.errors.DefaultRestExceptionHandler;
import org.labcabrera.samples.mongo.ddd.commons.api.predicate.PredicateParser;
import org.labcabrera.samples.mongo.ddd.commons.api.security.RepositoryDetailService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Import(SwaggerConfig.class)
@ComponentScan("org.labcabrera.samples.mongo.ddd.commons.api.hateoas.assemblers")
public class CommonsApiConfig {

	@Bean
	@ConditionalOnMissingBean
	UserDetailsService UserDetailsService() {
		return new RepositoryDetailService();
	}

	@Bean
	@ConditionalOnMissingBean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

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

	@Bean
	@ConditionalOnMissingBean
	PredicateParser pedicateParser() {
		return new PredicateParser();
	}

}
