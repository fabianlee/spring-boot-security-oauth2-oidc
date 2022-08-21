package org.fabianlee.springsecurityoauth2resource.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.fabianlee.springsecurityoauth2resource.oauth2.OAuth2Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/user")
public class UserController {

        private List userListV1 = new ArrayList(Arrays.asList(new User("moe"), new User("larry"), new User("curly")));
        
        @GetMapping(value="/test")
        public String test() {
        	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        	log.debug(auth.toString());
        	log.debug("auth authorities: " + auth.getAuthorities());
        	log.debug("===================");
        	JwtAuthenticationToken jwt = (JwtAuthenticationToken)auth;
        	log.debug(jwt.toString());
        	log.debug("jwt authorities: " + jwt.getAuthorities());
        	for(Map.Entry<String,Object> entry : jwt.getTokenAttributes().entrySet()) {
        		log.debug("att " + entry.getKey() + "=" + entry.getValue());
        	}
        	for(Map.Entry<String,Object> entry : jwt.getToken().getClaims().entrySet() ) {
        		log.debug("claim: " + entry.getKey() + "=" + entry.getValue());
        	}
        	return "test";
        }

        @GetMapping
        @PreAuthorize("hasRole('Domain Users')")
        public Iterable findAllUsers() {
            log.debug("doing findAllUsers");
            log.info("doing findAllUsers");
            log.warn("doing findAllUsers");
            log.error("doing findAllUsers");
            return userListV1;
        }
        
        @GetMapping(value="app1")
        @PreAuthorize("hasAuthority('app1')")
        public Iterable scope_app1() {
            log.debug("app1");
            return userListV1;
        }
        @GetMapping(value="app2")
        @PreAuthorize("hasAuthority('SCOPE_api_get')")
        public Iterable scope_app2() {
            log.debug("app2");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            log.debug("user email: " + ((JwtAuthenticationToken)authentication).getToken().getClaimAsString("email"));
            return userListV1;
        }
        
        @DeleteMapping
        @PreAuthorize("hasRole('managers')")
        public void deleteUser() {
            log.debug("called deleteUser");
            if (userListV1.size() > 0) {
                userListV1.remove(userListV1.size() - 1);
            }
        }
}
