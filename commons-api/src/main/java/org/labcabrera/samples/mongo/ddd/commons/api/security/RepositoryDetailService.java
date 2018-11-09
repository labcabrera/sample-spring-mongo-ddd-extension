package org.labcabrera.samples.mongo.ddd.commons.api.security;

import org.labcabrera.samples.mongo.ddd.commons.data.ApiUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class RepositoryDetailService implements UserDetailsService {

	@Autowired
	private ApiUserRepository apiUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return apiUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
	}

}