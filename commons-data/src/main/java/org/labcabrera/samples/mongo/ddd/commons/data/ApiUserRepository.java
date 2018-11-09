package org.labcabrera.samples.mongo.ddd.commons.data;

import java.util.Optional;

import org.labcabrera.samples.mongo.ddd.commons.model.security.ApiUser;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ApiUserRepository extends PagingAndSortingRepository<ApiUser, String> {

	Optional<ApiUser> findByUsername(String username);

}
