package org.bmartins.javasandbox.springmongosession.configuration;

import org.bmartins.javasandbox.springmongosession.session.MongoSession;
import org.bmartins.javasandbox.springmongosession.session.MongoSessionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;
import org.springframework.session.web.http.SessionRepositoryFilter;
  
@Configuration 
public class HttpSessionConfiguration {

	@Bean
	public <T extends ExpiringSession> SessionRepository<MongoSession> getSessionRepository() {
		return new MongoSessionRepository();
	}

    @Bean
    public <T extends ExpiringSession> SessionRepositoryFilter<T> springSessionRepositoryFilter(SessionRepository<T> sessionRepository) {
    	System.out.println("springSessionRepositoryFilter");
        SessionRepositoryFilter<T> sessionRepositoryFilter = new SessionRepositoryFilter<T>(sessionRepository);
        return sessionRepositoryFilter;
    }
    
}
