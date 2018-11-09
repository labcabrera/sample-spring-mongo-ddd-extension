package org.labcabrera.samples.mongo.ddd.commons.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;

	@Autowired
	private UserDetailsService userDetailsService;

	@Value("${app.security.unsecured.paths}")
	private String unsecuredPaths;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		log.debug("Configuring JWT security");
		String authorizationPath = env.getProperty("app.security.authorization.path");
		AuthenticationManager authenticationManager = authenticationManager();

		JwtAuthenticationFilter authenticationFilter = new JwtAuthenticationFilter(authenticationManager(), env);
		authenticationFilter.setFilterProcessesUrl(authorizationPath);

		//@formatter:off
		httpSecurity 
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
			.cors()
				.and()
			.csrf()
				.disable()
			.authorizeRequests()
				.antMatchers(unsecuredPaths.split(",")).permitAll()
				.antMatchers(HttpMethod.POST, authorizationPath).permitAll()
				.anyRequest().authenticated()
				.and()
			.addFilter(authenticationFilter)
			.addFilter(new JwtAuthorizationFilter(authenticationManager, env));
		//@formatter:on
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.debug("Configuring AuthenticationManager");
		auth.userDetailsService(userDetailsService);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
}