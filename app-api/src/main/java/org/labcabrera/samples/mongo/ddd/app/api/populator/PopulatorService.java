package org.labcabrera.samples.mongo.ddd.app.api.populator;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.labcabrera.samples.mongo.ddd.commons.data.ApiUserRepository;
import org.labcabrera.samples.mongo.ddd.commons.model.security.ApiUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PopulatorService {

	@Autowired
	private ApiUserRepository apiUserRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ObjectMapper mapper;

	public void check() {
		if (apiUserRepository.count() == 0L) {
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

	}

}
