package org.labcabrera.samples.mongo.ddd.app.api.populator;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.labcabrera.samples.mongo.ddd.app.model.ContractAD;
import org.labcabrera.samples.mongo.ddd.app.model.CustomerAD;
import org.labcabrera.samples.mongo.ddd.app.model.Product;
import org.labcabrera.samples.mongo.ddd.commons.data.ApiUserRepository;
import org.labcabrera.samples.mongo.ddd.commons.data.ContractRelationRepository;
import org.labcabrera.samples.mongo.ddd.commons.data.ContractRepository;
import org.labcabrera.samples.mongo.ddd.commons.data.CustomerRepository;
import org.labcabrera.samples.mongo.ddd.commons.model.Contract;
import org.labcabrera.samples.mongo.ddd.commons.model.ContractCustomerRelation;
import org.labcabrera.samples.mongo.ddd.commons.model.Customer;
import org.labcabrera.samples.mongo.ddd.commons.model.security.ApiUser;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
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

	private final MongoOperations mongoOperations;
	private final ApiUserRepository apiUserRepository;
	private final CustomerRepository<CustomerAD> customerRepository;
	private final ContractRepository<ContractAD> contractRepository;
	private final ContractRelationRepository<ContractAD, CustomerAD> relationRepository;
	private final ObjectMapper mapper;
	private final PasswordEncoder passwordEncoder;

	public void check() {
		checkApiUsers();
		checkProducts();
		checkCustomers();
		checkContracts();
		checkRelations();
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

	private void checkProducts() {
		if (mongoOperations.count(new Query(), Product.class) > 0) {
			return;
		}
		log.info("Populating products");
		try (
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("data/products.json")) {
			List<Product> products = mapper.readValue(in, new TypeReference<List<Product>>() {
			});
			mongoOperations.insertAll(products);
		}
		catch (IOException ex) {
			throw new RuntimeException("Error reading initial products", ex);
		}
	}

	private void checkCustomers() {
		if (customerRepository.count() > 0) {
			return;
		}
		log.info("Populating customers");
		try (InputStream in = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("data/customers.json")) {
			List<Customer<CustomerAD>> customers = mapper.readValue(in,
				new TypeReference<List<Customer<CustomerAD>>>() {
				});
			customerRepository.saveAll(customers);
		}
		catch (IOException ex) {
			throw new RuntimeException("Error reading initial customers", ex);
		}
	}

	private void checkContracts() {
		if (contractRepository.count() > 0) {
			return;
		}
		log.info("Populating contracts");
		try (InputStream in = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("data/contracts.json")) {
			List<Contract<ContractAD>> contracts = mapper.readValue(in,
				new TypeReference<List<Contract<ContractAD>>>() {
				});
			contractRepository.saveAll(contracts);
		}
		catch (IOException ex) {
			throw new RuntimeException("Error reading initial contracts", ex);
		}
	}

	private void checkRelations() {
		if (relationRepository.count() > 0) {
			return;
		}
		log.info("Populating relations");
		try (InputStream in = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("data/relations.json")) {
			List<ContractCustomerRelation<ContractAD, CustomerAD>> relations = mapper.readValue(
				in,
				new TypeReference<List<ContractCustomerRelation<ContractAD, CustomerAD>>>() {
				});
			for (ContractCustomerRelation<ContractAD, CustomerAD> i : relations) {
				Contract<ContractAD> contract = contractRepository
					.findByContractNumber(i.getContract().getContractNumber()).get();
				Customer<CustomerAD> customer = customerRepository
					.findByIdCardNumber(i.getCustomer().getIdCard().getNumber()).get();
				i.setContract(contract);
				i.setCustomer(customer);
			}
			relationRepository.saveAll(relations);
		}
		catch (IOException ex) {
			throw new RuntimeException("Error reading initial contracts", ex);
		}
	}
}
