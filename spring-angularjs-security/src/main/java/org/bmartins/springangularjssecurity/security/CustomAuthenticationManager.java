package org.bmartins.springangularjssecurity.security;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.bmartins.springangularjssecurity.model.Provider;
import org.bmartins.springangularjssecurity.model.User;
import org.bmartins.springangularjssecurity.service.UserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		logger.debug("authenticate - crednetials: {}, details: {}, name: {}, principal: {}, authenticated: {}", 
				authentication.getCredentials(), 
				authentication.getDetails(), 
				authentication.getName(), 
				authentication.getPrincipal(),
				authentication.isAuthenticated());
		
		if(authentication instanceof UsernamePasswordAuthenticationToken) {			
			return performAuthentication((UsernamePasswordAuthenticationToken)authentication);			
		} else if (authentication instanceof SocialAuthenticationToken) {			
			return performAuthentication((SocialAuthenticationToken)authentication);
		}
		
		return null;
	}
	
	private UserAuthenticationToken performAuthentication(SocialAuthenticationToken token) {
		logger.debug("SocialAuthenticationToken: {}", token);

		Connection<?> connection = token.getConnection();
		
		UserProfile userProfile = connection.fetchUserProfile();		
		logger.debug("UserProfile: {}", ToStringBuilder.reflectionToString(userProfile).toString());
		
		User user = userDetailsService.findByUsernameAndProvider(userProfile.getUsername(), Provider.fromString(token.getProviderId()));		
		if(user == null) {
			user = new User.Builder(userProfile.getUsername(), "[ignore]", (Collection<? extends GrantedAuthority>) new ArrayList<GrantedAuthority>())				
				.firstName(userProfile.getFirstName())				
				.lastName(userProfile.getLastName())
				.email(userProfile.getEmail())
				.provider(Provider.fromString(token.getProviderId()))
				.build();				
			
			userDetailsService.addUser(user);
		}
		
		return new UserAuthenticationToken
				.Builder(userProfile.getUsername(), 
						null, 
						token.getAuthorities())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.provider(user.getProvider())
				.profileImageUrl(connection.getImageUrl())
				.build();		
	}

	private UserAuthenticationToken performAuthentication(UsernamePasswordAuthenticationToken token) {
		User user = userDetailsService.findByUsername(token.getName());
		if(user != null) {
			if(token.getCredentials().equals(user.getPassword())) {
				return new UserAuthenticationToken
					.Builder(token.getPrincipal(), 
							token.getCredentials(), 
							token.getAuthorities())
					.firstName(user.getFirstName())
					.lastName(user.getLastName())					
					.build();
			}
		}			
		
		return null;
	}

}
