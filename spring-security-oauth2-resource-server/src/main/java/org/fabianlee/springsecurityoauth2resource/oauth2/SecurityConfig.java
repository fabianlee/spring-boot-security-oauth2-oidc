/**
 * For Spring 5.7.1 or greater, WebSecurityConfigurerAdapter is DEPRECATED !!!!
 * See the class SecurityConfiguration as replacement
 */
package org.fabianlee.springsecurityoauth2resource.oauth2;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.DelegatingJwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
@Override
protected void configure(HttpSecurity http) throws Exception {
	
    http
        .cors().and()
        .authorizeRequests(a -> a
            .antMatchers("/","/info","login**").permitAll()
            //.antMatchers("/api/**").hasRole("USER") // 'ROLE_' will be prefixed for us
            .anyRequest().authenticated()
            )
        .oauth2ResourceServer(oauth2 -> oauth2.jwt()
        		.jwtAuthenticationConverter(jwtAuthenticationConverter())
        		);
    
} // configure

//https://stackoverflow.com/questions/58205510/spring-security-mapping-oauth2-claims-with-roles-to-secure-resource-server-endp
private JwtAuthenticationConverter jwtAuthenticationConverter() {
	
/*
    // this allows us to convert only a single attribute to authorities 
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("group"); // default is: scope, scp
    jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_"); // default is: SCOPE_
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
*/

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
