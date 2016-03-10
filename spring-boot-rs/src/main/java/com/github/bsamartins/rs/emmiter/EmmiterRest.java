package com.github.bsamartins.rs.emmiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

@RestController
public class EmmiterRest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private EmmiterService emmiterService;
	
	@RequestMapping("/streaming")	
	public ResponseBodyEmitter streaming() throws Exception {
		ResponseBodyEmitter emmiter = new ResponseBodyEmitter(-1L);
		emmiter.onTimeout(() -> {
			logger.info("timeout");
		});

		logger.info("starting emmiter");
		emmiterService.start(emmiter);
		logger.info("done with emmiter");
		return emmiter;
	}
	

}
