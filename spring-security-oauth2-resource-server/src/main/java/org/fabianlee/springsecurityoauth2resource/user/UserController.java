package org.fabianlee.springsecurityoauth2resource.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.fabianlee.springsecurityoauth2resource.oauth2.OAuth2Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.ui.Model;
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
        
        @GetMapping(value="/me")
        public String me() {
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
        	
        	StringBuffer sbuf = new StringBuffer();
        	sbuf.append("email: " + jwt.getToken().getClaimAsString("email") + "\n");
        	sbuf.append("authorities: " + auth.getAuthorities() + "\n");
        	sbuf.append("group: " + jwt.getToken().getClaimAsString("group") + "\n");
        	sbuf.append("scp: " + jwt.getToken().getClaimAsString("scp") + "\n");
        	return sbuf.toString();
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
        
        @DeleteMapping
        @PreAuthorize("hasRole('managers') && hasAuthority('SCOPE_api_delete')")
        public Iterable deleteUser() {
        	int nusers = userListV1.size();
            log.debug("called deleteUser");
            if (userListV1.size() > 0) {
                userListV1.remove(userListV1.size() - 1);
            }
        	log.debug("deleteUser: #users was " + nusers + " and is now  " + userListV1.size());

            return userListV1;
        }
        
        @GetMapping(value="/engineer")
        @PreAuthorize("hasRole('engineers') || hasRole('managers')")
        public ResponseEntity<List<User>> listEngineers() {
            log.debug("listEngineers");
            
            List<User> users = new ArrayList<User>(
            		Arrays.asList(
            				new User("engineer1"),
            				new User("engineer2")
            				) 
            		);
            return new ResponseEntity<List<User>>(users,HttpStatus.OK);
        }
        
        @GetMapping(value="/manager")
        @PreAuthorize("hasRole('managers')")
        public ResponseEntity<List<User>> listManagers() {
            log.debug("listManagers");
            
            // log user email to show how audit entry could be created for sensitive info
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            log.debug("AUDIT info requested by: " + ((JwtAuthenticationToken)authentication).getToken().getClaimAsString("email"));
            
            List<User> managers = new ArrayList<User>( 
            		Arrays.asList(
            				new User("manager1")
            				) 
            		);
            return new ResponseEntity<List<User>>(managers,HttpStatus.OK);
        }
        
}
