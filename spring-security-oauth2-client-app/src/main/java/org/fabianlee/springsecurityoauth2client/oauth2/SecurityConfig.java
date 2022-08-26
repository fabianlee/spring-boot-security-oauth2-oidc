/**
 * For Spring Security 5.7.1+ OR Spring Boot 2.7.0+, WebSecurityConfigurerAdapter is DEPRECATED !!!!
 * Configure through component based security configuration instead, see my newer implementation here: 
 * https://github.com/fabianlee/spring-boot-security5-oauth2-oidc/blob/main/spring-security5-oauth2-client-app/src/main/java/org/fabianlee/springsecurityoauth2client/oauth2/SecurityConfig.java
 * 
 * https://www.springcloud.io/post/2022-02/spring-security-deprecate-websecurityconfigureradapter
 * https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
 */

package org.fabianlee.springsecurityoauth2client.oauth2;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
@Override
protected void configure(HttpSecurity http) throws Exception {
	
    http
    	//.cors().and()
    	//.addFilterBefore(myCORSFilter, LogoutFilter.class).authorizeRequests(a -> a
    	.authorizeRequests(a -> a
            .antMatchers("/","/info","login**").permitAll()
            .anyRequest().authenticated())
        .csrf(c -> 
        	c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        		)
        .logout(l -> l.logoutSuccessUrl("/").permitAll())
        .oauth2Login(o -> 
        	o.successHandler((request, response, authentication) -> {
                response.sendRedirect("/");
            })
        );
    
} // configure

    
} // class
