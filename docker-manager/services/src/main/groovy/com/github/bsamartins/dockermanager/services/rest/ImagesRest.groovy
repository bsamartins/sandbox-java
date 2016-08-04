package com.github.bsamartins.dockermanager.services.rest

import com.github.dockerjava.api.DockerClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.PathResource
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.annotation.Resource

/**
 * Created by martinsb on 19/05/2016.
 */
@RestController
@RequestMapping("/images")
class ImagesRest {

    @Autowired
    private DockerClient dockerClient;

    @RequestMapping("/")
    public List images() {
        return dockerClient.listImagesCmd().exec();
    }

}
