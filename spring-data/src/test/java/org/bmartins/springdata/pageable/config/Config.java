package org.bmartins.springdata.pageable.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configurable
@EnableJpaRepositories("org.bmartins.springdata.pageable.repository")
@EnableTransactionManagement
public class Config {
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
	    return new JpaTransactionManager(entityManagerFactory);
	}		
}