package org.bmartins.springangularjssecurity.service;

import org.bmartins.springangularjssecurity.model.Provider;
import org.bmartins.springangularjssecurity.model.User;

public interface UserDetailsService {
	
	User findByUsername(String username);
	User findByUsernameAndProvider(String username, Provider provider);
	void addUser(User user);
}
