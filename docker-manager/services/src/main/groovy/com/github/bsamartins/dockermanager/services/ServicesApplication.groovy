package com.github.bsamartins.dockermanager.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by martinsb on 18/05/2016.
 */
@SpringBootApplication
public class ServicesApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServicesApplication.class, args);
    }

}
