package org.bmartins.sandbox.springwebflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan
public class MainApplication extends SpringApplication {
    public static void main(String args[]) {
        run(MainApplication.class, args);
    }
}