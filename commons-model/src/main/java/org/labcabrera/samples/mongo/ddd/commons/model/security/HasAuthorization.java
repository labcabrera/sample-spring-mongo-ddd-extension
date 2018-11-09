package org.labcabrera.samples.mongo.ddd.commons.model.security;

import java.util.List;

public interface HasAuthorization {

	List<String> getAuthorization();

	void setAuthorization(List<String> permissions);

}
