package org.bmartins.javasandbox.springmongosession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ApplicationMain extends SpringApplication {	
	
	public static void main(String args[]) {		
		SpringApplication.run(ApplicationMain.class, args);
	}	
}
