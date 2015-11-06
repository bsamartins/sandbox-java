package org.bmartins.springangularjssecurity.security;

import java.util.Collection;

import org.bmartins.springangularjssecurity.model.Provider;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class UserAuthenticationToken extends AbstractAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Object principal;
	private Object credentials;
	
	private String firstName;
	private String lastName;
	private Provider provider;
	private String profileImageUrl;
	
	public UserAuthenticationToken(
			Object principal, 
			Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		super.setAuthenticated(true);
		
	}
	
	@Override
	public Object getCredentials() {
		return credentials;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	public static class Builder {
		private UserAuthenticationToken token;
		
		public Builder(Object principal, 
				Object credentials,
				Collection<? extends GrantedAuthority> authorities) {
			
			token = new UserAuthenticationToken(principal, credentials, authorities);			
		}
		
		public Builder firstName(String firstName) {
			token.setFirstName(firstName);
			return this;
		}

		public Builder lastName(String lastName) {
			token.setLastName(lastName);
			return this;
		}

		public Builder profileImageUrl(String profileImageUrl) {
			token.setProfileImageUrl(profileImageUrl);
			return this;
		}

		public Builder provider(Provider provider) {
			token.setProvider(provider);
			return this;
		}
		
		public UserAuthenticationToken build() {
			return token;
		}
		
	}

	public String getFirstName() {
		return firstName;
	}

	private void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	private void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Provider getProvider() {
		return provider;
	}
	
	private void setProvider(Provider provider) {
		this.provider = provider;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
}
