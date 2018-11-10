package org.labcabrera.samples.mongo.ddd.commons.serialization;

import java.io.IOException;

import org.labcabrera.samples.mongo.ddd.commons.model.Customer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@SuppressWarnings({ "serial", "rawtypes" })
public class BasicCustomerSerializer extends StdSerializer<Customer> {

	protected BasicCustomerSerializer() {
		super(Customer.class);
	}

	@Override
	public void serialize(Customer value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeStringField("id", value.getId());
		gen.writeStringField("name", value.getName());
		gen.writeStringField("surname", value.getSurname());
		gen.writeEndObject();
	}

}
