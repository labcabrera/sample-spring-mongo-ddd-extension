package org.labcabrera.samples.mongo.ddd.app.model;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.labcabrera.samples.mongo.ddd.commons.data.CustomerRepository;
import org.labcabrera.samples.mongo.ddd.commons.model.Customer;
import org.labcabrera.samples.mongo.ddd.commons.model.QCustomer;
import org.labcabrera.samples.mongo.ddd.commons.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import com.querydsl.core.types.Predicate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
public class CustomerServiceTest {

	@Autowired
	private CustomerRepository<CustomerAD> repository;

	@Autowired
	private CustomerService<CustomerAD> service;

	@Test
	public void test() {
		SecurityContext context = SecurityContextHolder.getContext();
		TestingAuthenticationToken token = new TestingAuthenticationToken("test", "test", "test");
		context.setAuthentication(token);

		Customer<CustomerAD> customer = new Customer<>();
		customer.setName("John");
		customer.setSurname("Doe");
		customer.setAdditionalData(new CustomerAD());
		customer.getAdditionalData().setCode("12345");
		customer.setAuthorization(Arrays.asList("test"));

		Customer<CustomerAD> saved = repository.save(customer);

		String id = saved.getId();
		Assert.assertNotNull(id);
		Assert.assertEquals(customer.getAdditionalData().getCode(), saved.getAdditionalData().getCode());

		Predicate predicate = QCustomer.customer.id.eq(id);
		Pageable pageable = PageRequest.of(0, 10);

		Page<Customer<CustomerAD>> page = service.findAll(predicate, pageable);

		Customer<CustomerAD> readed = page.getContent().get(0);
		Assert.assertEquals(customer.getAdditionalData().getCode(), readed.getAdditionalData().getCode());

		// Other user can read previous entity
		TestingAuthenticationToken otherUserToken = new TestingAuthenticationToken("user2", "user2", "user2");
		context.setAuthentication(otherUserToken);

		page = service.findAll(predicate, pageable);
		Assert.assertTrue(page.getContent().isEmpty());
	}

}
