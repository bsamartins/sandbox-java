package com.github.bsamartins.cloudmicroservices.testservice.service

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by martinsb on 19/04/2016.
 */
@RestController
@RequestMapping("/test")
class TestService {

    @RequestMapping("/")
    def index() {
        return "Hello World"
    }

}
