package org.bmartins.sandbox.springwebflow.config;

import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContainerConfiguration {
	
//	@Bean
//	public TomcatEmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory() {
//		return new TomcatEmbeddedServletContainerFactory();
//	}

	@Bean
	public JettyEmbeddedServletContainerFactory jettyEmbeddedServletContainerFactory() {
		return new JettyEmbeddedServletContainerFactory();
	}

}
