package com.github.bsamartins.eureka.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableAutoConfiguration
@EnableEurekaClient
@RestController
public class ClientApplication {

	@Autowired
	private DiscoveryClient discoveryClient;
	
	@RequestMapping("/")
	public String home() {		
		return "Hello World";
	}

	@RequestMapping("/system.out.println")
	public void println() {		
		System.out.println(discoveryClient.getServices());
	}

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}
}