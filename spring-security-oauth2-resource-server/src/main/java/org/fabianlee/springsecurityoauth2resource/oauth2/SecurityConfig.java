/**
 * For Spring Security 5.7.1+ OR Spring Boot 2.7.0+, WebSecurityConfigurerAdapter is DEPRECATED !!!!
 * Configure through component based security configuration instead, see my newer implementation here: 
 * https://github.com/fabianlee/spring-boot-security5-oauth2-oidc/blob/main/spring-security5-oauth2-resource-server/src/main/java/org/fabianlee/springsecurityoauth2resource/oauth2/SecurityConfig.java
 * 
 * https://www.springcloud.io/post/2022-02/spring-security-deprecate-websecurityconfigureradapter
 * https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
 */
package org.fabianlee.springsecurityoauth2resource.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.DelegatingJwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private MyCORSFilter myCORSFilter;
	
@Override
protected void configure(HttpSecurity http) throws Exception {
	
    http
    	.csrf().disable()
    	// our custom CORS filter will not work properly if this is used
    	// using custom filter because I could not get cors() to work properly in this version of Spring
    	// although it does work properly in newer versions using the newer SecurityFilterChain
        //.cors().and() //configurationSource(corsConfigurationSource()).and()
    	//
        .addFilterBefore(myCORSFilter, LogoutFilter.class).authorizeRequests(a -> a
            .antMatchers("/","/info","/infojson","/testjwt","login**").permitAll()
            .antMatchers(HttpMethod.OPTIONS).permitAll()
            .anyRequest().authenticated()
            )
        .oauth2ResourceServer(oauth2 -> oauth2.jwt()
        		.jwtAuthenticationConverter(jwtAuthenticationConverter())
        		);
    
} // configure

//https://stackoverflow.com/questions/58205510/spring-security-mapping-oauth2-claims-with-roles-to-secure-resource-server-endp
private JwtAuthenticationConverter jwtAuthenticationConverter() {
	
	// use converter that puts both SCOPE_ and ROLE_ into authorities
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter());
    return jwtAuthenticationConverter;
}

// https://github.com/spring-projects/spring-security/issues/10439 (jzheaux Josh Cummings)
public DelegatingJwtGrantedAuthoritiesConverter authoritiesConverter() {
    JwtGrantedAuthoritiesConverter scp = new JwtGrantedAuthoritiesConverter();
    // these are defaults and do not need to be set
    //scp.setAuthoritiesClaimName("scp");
    //scp.setAuthorityPrefix("SCOPE_");
    JwtGrantedAuthoritiesConverter roles = new JwtGrantedAuthoritiesConverter();
    roles.setAuthoritiesClaimName("group");
    roles.setAuthorityPrefix("ROLE_");
    return new DelegatingJwtGrantedAuthoritiesConverter(scp, roles);
}
    
} // class

