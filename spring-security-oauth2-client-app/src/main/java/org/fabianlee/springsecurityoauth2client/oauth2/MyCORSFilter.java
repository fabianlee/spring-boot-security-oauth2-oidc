package org.fabianlee.springsecurityoauth2client.oauth2;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyCORSFilter extends OncePerRequestFilter {

	@Override
	public void destroy() {

	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String origin = "http://localhost:8081";

		log.debug("in doFilterInternal");

		response.addHeader("Access-Control-Allow-Origin", origin);
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
		
		// cannot specify origin=* if using credentials
		response.setHeader("Access-Control-Allow-Credentials", "true");
		
		// cannot specify "*" if using credentials
		response.setHeader("Access-Control-Allow-Headers",
				"X-PINGOTHER,Access-Control-Allow-Headers,Access-Control-Allow-Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Origin,Cache-Control,Content-Type,Authorization,Host,Referer");

		filterChain.doFilter(request, response);

	}
}