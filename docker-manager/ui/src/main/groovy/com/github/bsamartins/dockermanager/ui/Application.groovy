package com.github.bsamartins.dockermanager.ui

import javafx.stage.Stage
import org.springframework.boot.Banner
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.support.GenericApplicationContext

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws Exception {

        ConfigurableApplicationContext ctx = new GenericApplicationContext();
        ctx.beanFactory.registerSingleton("primaryStage", stage)
        ctx.refresh()

        ConfigurableApplicationContext applicationCtx = new SpringApplicationBuilder(ApplicationConfiguration)
                .bannerMode(Banner.Mode.OFF)
                .parent(ctx)
                .run();
    }
}
