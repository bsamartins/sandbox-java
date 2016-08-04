package com.github.bsamartins.dockermanager.services

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.core.DockerClientBuilder
import com.github.dockerjava.core.DockerClientConfig

/**
 * Created by martinsb on 18/05/2016.
 */
public class TestApplication {
    public static void main(String[] args) {

        DockerClientConfig config = DockerClientConfig.createDefaultConfigBuilder()
            .withDockerHost("tcp://192.168.99.100:2376")
            .withDockerTlsVerify(true)
            .withDockerCertPath("/Users/martinsb/.docker/machine/machines/default")
            .build();

        DockerClient dockerClient = DockerClientBuilder.getInstance(config)
                .build();
        dockerClient.pingCmd().exec();
//        dockerClient.listImagesCmd().exec().each { i ->
//            println "id: ${i.id}, parentId: ${i.parentId}, tags: ${i.repoTags}"
//        }
    }
}
