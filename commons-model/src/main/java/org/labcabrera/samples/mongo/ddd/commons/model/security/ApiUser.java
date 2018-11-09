package org.labcabrera.samples.mongo.ddd.commons.model.security;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "apiUsers")
@Getter
@Setter
@ToString
@SuppressWarnings("serial")
public class ApiUser implements UserDetails {

	@Id
	private String id;

	private String username;
	private String password;
	private String email;
	private LocalDateTime expiration;
	private LocalDateTime locked;
	private LocalDateTime credentialsExpiration;
	private LocalDateTime activation;

	private List<String> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (roles == null) {
			return new ArrayList<>();
		}
		return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public boolean isAccountNonExpired() {
		return expiration == null || expiration.isAfter(LocalDateTime.now());
	}

	@Override
	public boolean isAccountNonLocked() {
		return locked == null || locked.isAfter(LocalDateTime.now());
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsExpiration == null || credentialsExpiration.isAfter(LocalDateTime.now());
	}

	@Override
	public boolean isEnabled() {
		return activation != null && activation.isBefore(LocalDateTime.now());
	}

}