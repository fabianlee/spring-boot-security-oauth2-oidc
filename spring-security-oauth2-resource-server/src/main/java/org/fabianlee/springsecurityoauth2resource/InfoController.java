package org.fabianlee.springsecurityoauth2resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;

@Controller
public class InfoController {

    // pull from build.gradle via 'springBoot' directive
    @Autowired
    private BuildProperties buildProperties;

    @RequestMapping(value="/info")
    public String info(Model model) {

    	model.addAttribute("buildProperties",buildProperties);
        return "info";

    }
   
}

