package org.bmartins.sandbox.springwebflow.config;

import java.util.LinkedHashSet;
import java.util.Set;

import org.bmartins.sandbox.springwebflow.config.support.EmbeddedContainerResourceResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.conditionalcomments.dialect.ConditionalCommentsDialect;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.extras.tiles2.dialect.TilesDialect;
import org.thymeleaf.extras.tiles2.spring4.web.configurer.ThymeleafTilesConfigurer;
import org.thymeleaf.extras.tiles2.spring4.web.view.FlowAjaxThymeleafTilesView;
import org.thymeleaf.extras.tiles2.spring4.web.view.ThymeleafTilesView;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.AjaxThymeleafViewResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@Configuration
@EnableWebMvc
public class WebConfiguration extends WebMvcConfigurerAdapter {	
	
	@Autowired
	private WebFlowConfig webFlowConfig;
	
	@Bean
	public ThymeleafTilesConfigurer tilesConfigurer() {
		ThymeleafTilesConfigurer configurer = new ThymeleafTilesConfigurer();
		configurer.setDefinitions("classpath:templates/**/views.xml");
		return configurer;
	}	
	
	@Bean
	public AjaxThymeleafViewResolver ajaxTilesViewResolver() {
		AjaxThymeleafViewResolver viewResolver = new AjaxThymeleafViewResolver();
		viewResolver.setViewClass(FlowAjaxThymeleafTilesView.class);
		viewResolver.setTemplateEngine(templateEngine());
		return viewResolver;
	}

	@Bean
	public ThymeleafViewResolver tilesViewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setViewClass(ThymeleafTilesView.class);
		viewResolver.setTemplateEngine(templateEngine());
		return viewResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine(){

		Set<IDialect> dialects = new LinkedHashSet<IDialect>();
		dialects.add(new TilesDialect());				
		dialects.add(new SpringSecurityDialect());
		dialects.add(new ConditionalCommentsDialect());

		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		templateEngine.setAdditionalDialects(dialects);
		return templateEngine;
	}
	
	@Bean
	public TemplateResolver templateResolver() {
		TemplateResolver templateResolver = new TemplateResolver();
		templateResolver.setResourceResolver(new EmbeddedContainerResourceResolver());
		templateResolver.setPrefix("templates/");
		templateResolver.setTemplateMode("HTML5");
		return templateResolver;
	}	

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		if (!registry.hasMappingForPattern("/webjars/**")) {
	        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	    }
		
		if (!registry.hasMappingForPattern("/**")) {
			registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/resources/", "classpath:/static/", "classpath:/META-INF/");
		}
 	}
	
	@Bean
	public DispatcherServlet dispatcherServlet() {
		return new DispatcherServlet();
	}
	
	@Bean
	public ServletRegistrationBean dispatcherServletRegistration() {
		ServletRegistrationBean registration = new ServletRegistrationBean(
				dispatcherServlet(), 
				"/*");
		return registration;
	}	
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
	}
	
	@Bean
	public FlowHandlerMapping flowHandlerMapping() {
		FlowHandlerMapping handlerMapping = new FlowHandlerMapping();
		handlerMapping.setOrder(-1);
		handlerMapping.setFlowRegistry(this.webFlowConfig.flowRegistry());
		return handlerMapping;
	}
	
	@Bean
	public FlowHandlerAdapter flowHandlerAdapter() {
		FlowHandlerAdapter handlerAdapter = new FlowHandlerAdapter();
		handlerAdapter.setFlowExecutor(this.webFlowConfig.flowExecutor());
		handlerAdapter.setSaveOutputToFlashScopeOnRedirect(true);
		return handlerAdapter;
	}
}
