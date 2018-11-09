package org.labcabrera.samples.mongo.ddd.app.model;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@SpringBootApplication
@EnableMongoRepositories(basePackages = "org.labcabrera.samples.mongo.ddd.commons.data")
public class TestConfiguration {

}
