package org.bmartins.springangularjssecurity.social;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;

public class DefaultGoogleConnectionFactoryBuilder {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private DefaultGoogleConnectionFactory googleConnectionFactory;
	private LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
	
	public DefaultGoogleConnectionFactoryBuilder(String clientId, String clientSecret) {
		googleConnectionFactory = new DefaultGoogleConnectionFactory(clientId, clientSecret);
	}
	
	public DefaultGoogleConnectionFactoryBuilder addScopeAttribute(String attribute) {
		parameters.add("scope", attribute);			
		return this;			
	}
	
	public DefaultGoogleConnectionFactory build() {			
		googleConnectionFactory.setScope(buildScope());			
		return this.googleConnectionFactory;
	}

	private String buildScope() {
		List<String> scopeParameter = parameters.getOrDefault("scope", new ArrayList<String>());
		List<String> scopeList = new ArrayList<>(scopeParameter);

		String defaultScope[] = StringUtils.split(StringUtils.defaultString(googleConnectionFactory.getScope()));
		Collections.addAll(scopeList, defaultScope);
		
		String scope = org.springframework.util.StringUtils.collectionToDelimitedString(scopeList, " ");
		
		logger.debug("scope: {}", scope);
		return scope;
	}
	
}