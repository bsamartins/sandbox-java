package org.bmartins.springangularjssecurity.service.impl;

import org.bmartins.springangularjssecurity.model.Provider;
import org.bmartins.springangularjssecurity.model.User;
import org.bmartins.springangularjssecurity.repository.UserRepository;
import org.bmartins.springangularjssecurity.service.UserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User findByUsername(String username) throws UsernameNotFoundException {
		logger.debug("findByUsername: username={}", username);
		return userRepository.findById(username);
	}

	@Override
	public User findByUsernameAndProvider(String username, Provider provider) {
		logger.debug("findByUsername: username={}, providerId={}", username, provider);
		return userRepository.findByIdAndProviderId(username, provider);
	}

	@Override
	public void addUser(User newUser) {
		userRepository.addUser(newUser);
	}
}
