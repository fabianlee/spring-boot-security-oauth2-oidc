package org.fabianlee.springsecurityoauth2resource.oauth2;

import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
@Configuration
public class SSLConfig {

	// if we wanted to pull from environmental properties
    //@Autowired
    //private Environment env;

    @PostConstruct
    private void configureSSL() {
      File absFile = null;
      try {
        absFile = ResourceUtils.getFile("classpath:mycacerts");
        log.debug("mycacerts classpath real location: " + absFile.exists() + " at " + absFile.toPath());
      }catch (Exception exc) {
      }
      String trustStoreLocation = absFile.toPath().toString(); //"src/main/resources/mycacerts";
      String trustStorePassword = "changeit";
      System.setProperty("javax.net.ssl.trustStore", trustStoreLocation ); 
      System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword );

    }
}
