package org.bmartins.sandbox.springwebflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.extras.tiles2.spring4.web.configurer.ThymeleafTilesConfigurer;

@Controller
public class HomeController {
	
	@Autowired
	private ThymeleafTilesConfigurer tilesConfigurer;
	
	@RequestMapping({"/home", "/"})
	public String index() {		
		return "index";
	}
	
}
