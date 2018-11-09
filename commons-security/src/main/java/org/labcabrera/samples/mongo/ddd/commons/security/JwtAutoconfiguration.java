package org.labcabrera.samples.mongo.ddd.commons.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(JwtSecurityConfig.class)
public class JwtAutoconfiguration {

}
