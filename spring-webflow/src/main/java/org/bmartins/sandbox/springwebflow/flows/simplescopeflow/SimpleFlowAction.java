package org.bmartins.sandbox.springwebflow.flows.simplescopeflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SimpleFlowAction {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleFlowAction.class); 
	
	public void log(String message) {
		LOG.info(message);
	}
}
