package org.bmartins.sandbox.springbatch.simpletask;

import org.springframework.xd.module.options.spi.ModuleOption;

public class SimpleBatchOptions {
	
	String path = null;
	
	@ModuleOption("path")
	public void setPath(String path) {
		this.path = path;
	}    	
}