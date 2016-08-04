package com.github.bsamartins.dockermanager.services.config

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.core.DockerClientBuilder
import com.github.dockerjava.core.DockerClientConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.retry.annotation.EnableRetry

/**
 * Created by martinsb on 18/05/2016.
 */
@Configuration
@ComponentScan
class ApplicationConfiguration {

    @Bean
    public DockerClient dockerClient() {
        DockerClientConfig config = DockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("tcp://192.168.99.100:2376")
                .withDockerTlsVerify(true)
                .withDockerCertPath("/Users/martinsb/.docker/machine/machines/default")
                .build();

        DockerClient dockerClient = DockerClientBuilder.getInstance(config)
                .build();

        return dockerClient;
    }
}
