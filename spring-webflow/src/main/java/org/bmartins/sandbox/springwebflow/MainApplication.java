package org.bmartins.sandbox.springwebflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages="org.bmartins.sandbox.springwebflow")
public class MainApplication extends SpringApplication {
    public static void main(String args[]) {
    	new SpringApplicationBuilder()
        	.showBanner(false)
        	.sources(MainApplication.class)
        	.run(args);
    }
}