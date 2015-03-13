package org.bmartins.sandbox.springwebflow.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class WebFlowConfig {	
	
	@Autowired
	private WebConfiguration webMvcConfig;
	
//	@Bean
//	public FlowDefinitionRegistry flowRegistry(ServletContextTemplateResolver templateResolver) {
//		return getFlowDefinitionRegistryBuilder(flowBuilderServices(templateResolver))
//				.setBasePath("/WEB-INF")
//				.addFlowLocationPattern("/**/*-flow.xml").build();
//	}
	
//	@Bean
//	public FlowBuilderServices flowBuilderServices(ServletContextTemplateResolver templateResolver) {
//		return getFlowBuilderServicesBuilder()
//				.setViewFactoryCreator(mvcViewFactoryCreator())
//				.setValidator(validator())
//				.setDevelopmentMode(true)
//				.build();
//	}
	
//	@Bean
//	public MvcViewFactoryCreator mvcViewFactoryCreator() {
//		MvcViewFactoryCreator factoryCreator = new MvcViewFactoryCreator();
//		factoryCreator.setViewResolvers(Arrays.<ViewResolver>asList(webMvcConfig.tilesViewResolver()));
//		factoryCreator.setUseSpringBeanBinding(true);
//		return factoryCreator;
//	}
	
	@Bean
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}
}
