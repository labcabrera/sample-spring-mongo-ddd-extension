package org.labcabrera.samples.mongo.ddd.app.api.populator;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.labcabrera.samples.mongo.ddd.commons.data.ApiUserRepository;
import org.labcabrera.samples.mongo.ddd.commons.data.CustomerRepository;
import org.labcabrera.samples.mongo.ddd.commons.model.Customer;
import org.labcabrera.samples.mongo.ddd.commons.model.security.ApiUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class PopulatorService {

	private final ApiUserRepository apiUserRepository;
	private final CustomerRepository customerRepository;
	private final ObjectMapper mapper;
	private final PasswordEncoder passwordEncoder;

	public void check() {
		checkApiUsers();
		checkCustomers();
	}

	private void checkApiUsers() {
		if (apiUserRepository.count() > 0) {
			return;
		}
		log.info("Populating API users");
		try (InputStream in = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("data/api-users.json")) {
			List<ApiUser> users = mapper.readValue(in, new TypeReference<List<ApiUser>>() {
			});
			users.stream().forEach(x -> x.setPassword(passwordEncoder.encode(x.getPassword())));
			apiUserRepository.saveAll(users);
		}
		catch (IOException ex) {
			throw new RuntimeException("Error reading initial api users", ex);
		}
	}

	private void checkCustomers() {
		if (customerRepository.count() > 0) {
			return;
		}
		log.info("Populating customers");
		try (InputStream in = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("data/customers.json")) {
			List<Customer> customers = mapper.readValue(in, new TypeReference<List<Customer>>() {
			});
			customerRepository.saveAll(customers);
		}
		catch (IOException ex) {
			throw new RuntimeException("Error reading initial api users", ex);
		}
	}
}
