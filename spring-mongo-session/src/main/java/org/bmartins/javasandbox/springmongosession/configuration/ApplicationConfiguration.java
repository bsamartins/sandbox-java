package org.bmartins.javasandbox.springmongosession.configuration;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@EnableWebMvc
@Configuration
public class ApplicationConfiguration {
	
	@PostConstruct
	private void init() {
		System.out.println("init");		
	}	
	
	@Bean 
	public UrlBasedViewResolver urlBaseViewResolver() {
		UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
		viewResolver.setViewClass(JstlView.class);
		return viewResolver;
	}
}
