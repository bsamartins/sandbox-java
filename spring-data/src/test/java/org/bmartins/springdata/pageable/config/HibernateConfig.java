package org.bmartins.springdata.pageable.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configurable
public class HibernateConfig {
				
	@Bean 
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter(); 
		vendorAdapter.setGenerateDdl(true); 
		vendorAdapter.setShowSql(true);     
    
	    Properties jpaProperties = new Properties(); 
	    jpaProperties.put("hibernate.hbm2ddl.auto", "create");
	    jpaProperties.put("hibernate.dialect", org.hibernate.dialect.H2Dialect.class.getName());
	    
	    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	    factory.setDataSource(dataSource); 
	    factory.setJpaVendorAdapter(vendorAdapter); 
	    factory.setPackagesToScan("org.bmartins.springdata.pageable"); 
	    factory.setJpaProperties(jpaProperties);
	    //factory.setValidationMode(ValidationMode.NONE)
	    factory.afterPropertiesSet(); 
	    factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());    
	    return factory; 
	}		  
	
}