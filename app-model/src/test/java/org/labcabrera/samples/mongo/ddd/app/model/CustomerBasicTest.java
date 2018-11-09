package org.labcabrera.samples.mongo.ddd.app.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.labcabrera.samples.mongo.ddd.commons.data.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
public class CustomerBasicTest {

	@Autowired
	private CustomerRepository repository;

	@Test
	public void test() {
		AppCustomer customer = new AppCustomer();
		customer.setName("Test");
		customer.setCode("12345");

		AppCustomer saved = repository.save(customer);

		String id = saved.getId();
		Assert.assertNotNull(id);
		Assert.assertEquals(customer.getCode(), saved.getCode());

		// TODO generics
		AppCustomer readed = (AppCustomer) repository.findById(id).get();

		Assert.assertEquals(customer.getCode(), readed.getCode());
	}

}
