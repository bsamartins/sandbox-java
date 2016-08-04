package com.github.bsamartins.dockermanager.services.config

import com.github.bsamartins.dockermanager.services.dockermachine.DockerMachineService
import org.springframework.context.annotation.Bean

/**
 * Created by martinsb on 27/06/2016.
 */
class ServicesConfiguration {

    @Bean
    DockerMachineService dockerMachineService() {
        new DockerMachineService()
    }
}
