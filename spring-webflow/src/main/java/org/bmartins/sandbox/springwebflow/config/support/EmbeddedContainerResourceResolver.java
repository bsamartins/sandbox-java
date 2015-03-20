package org.bmartins.sandbox.springwebflow.config.support;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.resourceresolver.IResourceResolver;

public class EmbeddedContainerResourceResolver implements IResourceResolver {
	
	private Logger LOG = LoggerFactory.getLogger(EmbeddedContainerResourceResolver.class); 
	
	@Override
	public InputStream getResourceAsStream(
			TemplateProcessingParameters templateProcessingParameters,
			String resourceName) {
		try {
			LOG.trace("Retrieving resource: {}", resourceName);
			ClassPathResource resource = new ClassPathResource(resourceName);
			if(resource.exists()) {
				return resource.getInputStream();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;		
	}

	@Override
	public String getName() {				
		return "EmbeddedContainerResourceResolver";
	}
}