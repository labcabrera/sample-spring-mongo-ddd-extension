package org.labcabrera.samples.mongo.ddd.commons.security;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final Integer expiration;
	private final String secret;
	private final String issuer;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, Environment env) {
		this.authenticationManager = authenticationManager;
		this.expiration = env.getProperty("app.security.jwt.expiration", Integer.class);
		this.secret = env.getProperty("app.security.jwt.secret");
		this.issuer = env.getProperty("spring.application.name");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.authentication.
	 * UsernamePasswordAuthenticationFilter#attemptAuthentication(javax.servlet.
	 * http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
		throws AuthenticationException {
		log.debug("Attempting authentication");
		try {
			String header = request.getHeader(JwtConstants.HeaderAuthorization);
			Assert.notNull(header, "Missing header " + JwtConstants.HeaderAuthorization);
			Assert.isTrue(header.startsWith("Basic "), "Expected basic authorization header");
			String b64 = header.replace("Basic ", "");
			String decoded = new String(Base64.getDecoder().decode(b64), Charset.forName("UTF-8"));
			int index = decoded.indexOf(":");
			Assert.isTrue(index > 0, "Invalid credentials");
			String username = decoded.substring(0, index);
			String password = decoded.substring(index + 1, decoded.length());
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken( // @formatter:off
					username, password, new ArrayList<>()); // @formatter:on
			return authenticationManager.authenticate(token);
		}
		catch (Exception ex) {
			throw new InternalAuthenticationServiceException("Authentication error", ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.authentication.
	 * AbstractAuthenticationProcessingFilter#successfulAuthentication(javax.
	 * servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * javax.servlet.FilterChain, org.springframework.security.core.Authentication)
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication auth) throws IOException, ServletException {
		String token = createToken(auth);
		response.addHeader(JwtConstants.HeaderAuthorization, JwtConstants.TokenBearerPrefix + " " + token);
	}

	private String createToken(Authentication auth) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expirationDate = now.plusMinutes(expiration);
		ZoneId zoneId = ZoneId.systemDefault();
		String username = ((UserDetails) auth.getPrincipal()).getUsername();
		List<String> roles = auth.getAuthorities().stream().map(x -> x.getAuthority()).collect(Collectors.toList());
		String token = Jwts.builder() // @formatter:off
				.setIssuedAt(Date.from(now.atZone(zoneId).toInstant()))
				.setExpiration(Date.from(expirationDate.atZone(zoneId).toInstant())).setIssuer(issuer)
				.setSubject(username).claim(JwtConstants.KeyClaimRoles, roles)
				.signWith(SignatureAlgorithm.HS512, secret).compact(); // @formatter:on
		return token;
	}
}