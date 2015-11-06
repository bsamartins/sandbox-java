package org.bmartins.springangularjssecurity.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.bmartins.springangularjssecurity.model.Provider;
import org.bmartins.springangularjssecurity.model.User;
import org.bmartins.springangularjssecurity.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

	private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
	
	private List<User> users = new ArrayList<User>();
	
	@Override
	public User findById(String id) {
		logger.debug("findById: {}", id);

		return users.stream()
			.filter(e -> true)
			.findFirst()
			.orElse(null);
	}

	public void addUser(User user) {
		users.add(user);
	}
	
	@PostConstruct
	public void init() {
		users.add(new User("admin@test.com", "password", new ArrayList<GrantedAuthority>(), "Bernardo", "Martins", "admin@test.com"));
	}

	@Override
	public User findByIdAndProviderId(String username, Provider provider) {
		logger.debug("findByIdAndProviderId - username: {}, provider: {}", username, provider);
		return users.stream()
			.filter(u -> u.getProvider() == provider)
			.filter(u -> u.getUsername().equals(username))
			.findFirst()
			.orElse(null);
	}		
}
