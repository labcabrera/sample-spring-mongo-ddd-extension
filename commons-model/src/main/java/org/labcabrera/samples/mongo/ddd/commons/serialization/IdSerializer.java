package org.labcabrera.samples.mongo.ddd.commons.serialization;

import java.io.IOException;

import org.labcabrera.samples.mongo.ddd.commons.model.HasId;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@SuppressWarnings("serial")
public class IdSerializer extends StdSerializer<HasId> {

	protected IdSerializer() {
		super(HasId.class);
	}

	@Override
	public void serialize(HasId value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeStringField("id", value.getId());
		gen.writeEndObject();
	}

}
