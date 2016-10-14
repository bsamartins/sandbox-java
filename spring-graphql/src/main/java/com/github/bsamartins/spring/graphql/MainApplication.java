package com.github.bsamartins.spring.graphql;

import com.oembedler.moon.graphql.boot.EnableGraphQLServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by martinsb on 13/10/2016.
 */
@SpringBootApplication
@EnableGraphQLServer
public class MainApplication {

    public static void main(String... args) {
        SpringApplication.run(MainApplication.class);
    }
}
