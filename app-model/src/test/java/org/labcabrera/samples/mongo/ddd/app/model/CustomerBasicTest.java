package org.labcabrera.samples.mongo.ddd.app.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.labcabrera.samples.mongo.ddd.commons.data.CustomerRepository;
import org.labcabrera.samples.mongo.ddd.commons.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
public class CustomerBasicTest {

	@Autowired
	private CustomerRepository<CustomerAD> repository;

	@Test
	public void test() {
		Customer<CustomerAD> customer = new Customer<>();
		customer.setName("Test");
		customer.setAdditionalData(new CustomerAD());
		customer.getAdditionalData().setCode("12345");

		Customer<CustomerAD> saved = repository.save(customer);

		String id = saved.getId();
		Assert.assertNotNull(id);
		Assert.assertEquals(customer.getAdditionalData().getCode(), saved.getAdditionalData().getCode());

		// TODO generics
		Customer<CustomerAD> readed = repository.findById(id).get();

		Assert.assertEquals(customer.getAdditionalData().getCode(), readed.getAdditionalData().getCode());
	}

}
