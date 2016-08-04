package com.github.bsamartins.dockermanager.ui

import com.github.bsamartins.dockermanager.engine.configuration.EngineConfiguration
import com.github.bsamartins.dockermanager.ui.controls.Browser
import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.stage.Stage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ExitCodeGenerator
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.web.servlet.config.annotation.EnableWebMvc

/**
 * Created by martinsb on 27/06/2016.
 */
@Configuration
@Import([EngineConfiguration])
@EnableWebMvc
class ApplicationConfiguration extends WebMvcAutoConfiguration {

    @Autowired ApplicationContext context

    private Integer port

    @Bean
    public ApplicationListener<EmbeddedServletContainerInitializedEvent> embeddedServletContainerInitializedEvent() {
        return new ApplicationListener<EmbeddedServletContainerInitializedEvent>() {
            @Override
            void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
                setPort(event.source.port)
            }
        }
    }

    @Bean
    @Autowired
    public ApplicationListener<ApplicationReadyEvent> applicationReadyEvent(Stage stage) {
        return new ApplicationListener<ApplicationReadyEvent>() {
            @Override
            void onApplicationEvent(ApplicationReadyEvent event) {
                // create the scene
                stage.setTitle("Web View")
                Scene scene = new Scene(new Browser("http://localhost:${getPort()}/index.html"),750,500, Color.web("#666970"))
                stage.setScene(scene)
                scene.getStylesheets().add("webviewsample/BrowserToolbar.css")
                stage.setOnHiding({ e ->
                    Platform.runLater({
                        SpringApplication.exit(context, new ExitCodeGenerator() {
                            @Override
                            int getExitCode() {
                                return 0
                            }
                        })
                    })
                })
                stage.show()
            }
        }
    }

    private void setPort(int port) {
        this.port = port
    }

    private int getPort() {
        return this.port
    }

}
