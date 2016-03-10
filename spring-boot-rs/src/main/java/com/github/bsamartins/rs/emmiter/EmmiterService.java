package com.github.bsamartins.rs.emmiter;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

@Service
public class EmmiterService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Async
	public Future<Void> start(final ResponseBodyEmitter emmiter) throws IOException {
		long startTime = System.currentTimeMillis();
		while(true) {
			logger.info("emmitting");
			try {
				emmiter.send(new Date(), MediaType.APPLICATION_JSON);
				if((System.currentTimeMillis() - startTime) > 30000) {
					emmiter.complete();
					break;
				} else {
					Thread.sleep(2000);
				}
				
			} catch (Exception e) {					
				emmiter.completeWithError(e);
				logger.error("Error", e);
				break;				
			}
		}			
		return new AsyncResult<Void>(null);
	}
	
}
