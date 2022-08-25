package org.fabianlee.springsecurityoauth2resource.oauth2;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyCORSFilter extends OncePerRequestFilter {
	
	@Value("#{systemEnvironment['ORIGIN_FORCE']}")
	private String originForce;
	
	@Override
	public void destroy() {

	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		log.debug("CORS filter " + request.getMethod() + ":" + request.getRequestURI());
		response.addHeader("Cache-Control", "no-cache");
		
		// try to guess origin from request header from client
		String requestOrigin = request.getHeader("Origin");
		if (originForce!=null) {
			requestOrigin = originForce;
		}
		response.addHeader("Access-Control-Allow-Origin",requestOrigin);
		
		response.setHeader("Access-Control-Allow-Credentials", "true");
		// make available for client use, in our case to read error messages coming back from 403 
		response.setHeader("Access-Control-Expose-Headers","WWW-Authenticate, Content-Type");
		
		boolean isOptions = "OPTIONS".equals(request.getMethod());
		// OPTIONS preflight checks require these headers
		if(isOptions) {
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
			
			// cannot specify "*" if using credentials
			// headers sent during OPTIONS preflight that are permitted to be used with main request
			response.setHeader("Access-Control-Allow-Headers",
					"Authorization"
					);
					//"Origin, Accept, X-Requested-With, WWW-Authenticate, Content-Type, Authorization"
					//"WWW-Authenticate,X-PINGOTHER,Access-Control-Allow-Headers,Access-Control-Allow-Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Origin,Cache-Control,Content-Type,Authorization,Host,Referer");
		}
		
		filterChain.doFilter(request, response);

	}
}