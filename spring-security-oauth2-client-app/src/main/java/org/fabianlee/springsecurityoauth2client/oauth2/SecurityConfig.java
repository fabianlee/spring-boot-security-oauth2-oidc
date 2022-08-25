/**
 * For Spring 5.7.1 or greater, WebSecurityConfigurerAdapter is DEPRECATED !!!!
 * See the class SecurityConfiguration as replacement
 */
package org.fabianlee.springsecurityoauth2client.oauth2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.fabianlee.springsecurityoauth2client.SpringMainApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private MyCORSFilter myCORSFilter;
	
@Override
protected void configure(HttpSecurity http) throws Exception {
	
    http
        //.cors().configurationSource(corsConfigurationSource()).and() // bean named corsConfigurationSource used by default, https://stackoverflow.com/questions/36968963/how-to-configure-cors-in-a-spring-boot-spring-security-application
    	.cors().and()
    	.addFilterBefore(myCORSFilter, LogoutFilter.class).authorizeRequests(a -> a
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

/*
@Bean
CorsConfigurationSource corsConfigurationSource() {
	log.trace("*********************** CORS Configuration source *******************************");
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:8081")); // *
    configuration.setAllowedMethods(Arrays.asList("GET","POST","DELETE","OPTIONS","PUT","OPTIONS"));
    //configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Origin", "Content-Type", "Accept")); // *
    configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Headers","Access-Control-Allow-Origin","Access-Control-Request-Method", "Access-Control-Request-Headers","Origin","Cache-Control", "Content-Type", "Authorization"));
    configuration.setAllowCredentials(true);
    configuration.setExposedHeaders(List.of("Authorization"));
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/access", configuration);
    return source;
}
*/
    
} // class
