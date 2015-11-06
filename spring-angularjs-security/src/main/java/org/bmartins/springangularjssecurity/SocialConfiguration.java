package org.bmartins.springangularjssecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

@Configuration
public class SocialConfiguration extends SocialConfigurerAdapter {
	
	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
		connectionFactoryConfigurer.addConnectionFactory(new TwitterConnectionFactory("eJx7Tm41Kt6r8ypxhJZVdMq8P", "D02bFkNuSPPD4hgPIabjK70ClexXuuYRpa7QwjG9kFL9UQ9dsg"));
	}
	
}
