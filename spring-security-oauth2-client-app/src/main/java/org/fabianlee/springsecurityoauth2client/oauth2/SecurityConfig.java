/**
 * For Spring 5.7.1 or greater, WebSecurityConfigurerAdapter is DEPRECATED !!!!
 * See the class SecurityConfiguration as replacement
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
