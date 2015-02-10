package org.bmartins.javasandbox.springmongosession.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServerConfiguration {
	
	private static final Integer DEFAULT_SERVER_PORT = 8080;
	
	@Value("#{systemProperties['server.port']}")
	private String serverPort;
	
	public Integer getServerPort() {
		return serverPort == null ? DEFAULT_SERVER_PORT : new Integer(serverPort);
	}
}
