package org.bmartins.springangularjssecurity.repository;

import org.bmartins.springangularjssecurity.model.Provider;
import org.bmartins.springangularjssecurity.model.User;


public interface UserRepository {
	
	User findById(String id);
	User findByIdAndProviderId(String username, Provider provider);
	void addUser(User user);
}
