package org.bmartins.sandbox.springaspectj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfiguration extends WebMvcConfigurerAdapter {	
			
	@Bean
	public DispatcherServlet dispatcherServlet() {
		return new DispatcherServlet();
	}
}
