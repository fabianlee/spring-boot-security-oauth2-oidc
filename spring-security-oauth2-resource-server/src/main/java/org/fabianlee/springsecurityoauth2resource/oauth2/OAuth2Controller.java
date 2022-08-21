package org.fabianlee.springsecurityoauth2resource.oauth2;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.SpringTemplateEngine;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class OAuth2Controller {
	
    // template engine, used for text templates
    @Autowired
    protected SpringTemplateEngine mMessageTemplateEngine;
    
    @GetMapping(value="/")
    public String index(Model model) {
    	return "index";
    }

}
