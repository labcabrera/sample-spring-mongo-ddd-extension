package org.labcabrera.samples.mongo.ddd.app.api;

import org.labcabrera.samples.mongo.ddd.commons.api.CommonsApiConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "org.labcabrera.samples.mongo.ddd.commons.data")
@Import(CommonsApiConfig.class)

// TODO
@ComponentScan("org.labcabrera.samples.mongo.ddd.commons.service")

public class ApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}

}
