package org.bmartins.sandbox.springaspectj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MainApplication extends SpringApplication {
    public static void main(String args[]) {
    	new SpringApplicationBuilder()
        	.showBanner(false)
        	.sources(MainApplication.class)
        	.run(args);
    }
}