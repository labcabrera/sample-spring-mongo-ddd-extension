package org.labcabrera.samples.mongo.ddd.app.api;

import org.labcabrera.samples.mongo.ddd.app.api.populator.PopulatorService;
import org.labcabrera.samples.mongo.ddd.commons.api.CommonsApiConfig;
import org.labcabrera.samples.mongo.ddd.commons.service.CommonsServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = { "org.labcabrera.samples.mongo.ddd.commons.data",
	"org.labcabrera.samples.mongo.ddd.app.api.repositories" })
@Import({ CommonsApiConfig.class, CommonsServiceConfig.class })
public class ApiApplication implements CommandLineRunner {

	@Autowired
	private PopulatorService populatorService;

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		populatorService.check();
	}

}
