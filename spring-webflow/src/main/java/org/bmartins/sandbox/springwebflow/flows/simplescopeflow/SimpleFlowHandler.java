package org.bmartins.sandbox.springwebflow.flows.simplescopeflow;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.webflow.core.FlowException;
import org.springframework.webflow.execution.FlowExecutionOutcome;
import org.springframework.webflow.execution.repository.NoSuchFlowExecutionException;
import org.springframework.webflow.mvc.servlet.AbstractFlowHandler;

public class SimpleFlowHandler extends AbstractFlowHandler {
	
    private String flowId = "/flows/simpleScope";
    
    public SimpleFlowHandler(String flowId) {  
    	this.flowId = flowId;
	}
    
    @Override
    public String handleExecutionOutcome(FlowExecutionOutcome outcome, HttpServletRequest request,
	    HttpServletResponse response) {
    	return flowId;
    }

    @Override
    public String handleException(FlowException e, HttpServletRequest request, HttpServletResponse response) {
    	if (e instanceof NoSuchFlowExecutionException) {
    		return flowId;
    	} else {
    		throw e;
    	}
    }

    @Override
    public String getFlowId() {    	
    	return flowId;
    }
    
    
}
