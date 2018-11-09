package org.labcabrera.samples.mongo.ddd.commons.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private final String secret;

	public JwtAuthorizationFilter(AuthenticationManager authManager, Environment env) {
		super(authManager);
		this.secret = env.getProperty("app.security.jwt.secret");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.web.authentication.www.BasicAuthenticationFilter
	 * #doFilterInternal(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		String header = request.getHeader(JwtConstants.HeaderAuthorization);
		if (header == null || !header.startsWith(JwtConstants.TokenBearerPrefix)) {
			chain.doFilter(request, response);
			return;
		}
		try {
			UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(request, response);
		}
		catch (SignatureException ex) {
			handleException(ex, response);
		}
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		UsernamePasswordAuthenticationToken result = null;
		String header = request.getHeader(JwtConstants.HeaderAuthorization);
		if (header != null) {
			log.debug("JWT validation attempt ({} {})", request.getMethod(), request.getRequestURI());
			String token = header.replace(JwtConstants.TokenBearerPrefix, "");
			Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			String user = claims.getBody().getSubject();
			if (user != null) {
				List<GrantedAuthority> grantedAuthorities = readGrantedAuthorities(claims);
				result = new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities);
				log.debug("Granted authorities: {}", result.getAuthorities());
			}
			else {
				log.debug("Missing subject in JWT token");
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private List<GrantedAuthority> readGrantedAuthorities(Jws<Claims> claims) {
		List<GrantedAuthority> result = new ArrayList<>();
		ArrayList<String> roles = (ArrayList<String>) claims.getBody().get(JwtConstants.KeyClaimRoles);
		if (roles != null) {
			roles.forEach(i -> result.add(new SimpleGrantedAuthority(i)));
		}
		return result;
	}

	private void handleException(Exception ex, HttpServletResponse response) {
		log.debug("Invalid JWT token", ex);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}
}