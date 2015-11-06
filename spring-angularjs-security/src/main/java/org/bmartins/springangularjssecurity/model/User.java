package org.bmartins.springangularjssecurity.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUser;

public class User extends SocialUser {
	
	private static final long serialVersionUID = 1L;
	
	private String firstName;
	private String lastName;
	private String email;
	private Provider provider;
	
	public User(String username, 
			String password, 
			boolean enabled, 
			boolean accountNonExpired, 
			boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public User(String username, String password, Collection<? extends GrantedAuthority> authorities, String firstName, String lastName, String email) {
		super(username, password, authorities);
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Provider getProvider() {
		return provider;
	}

	private void setProvider(Provider provider) {
		this.provider = provider;
	}	
	
	public boolean isExternalProvider() {
		return this.provider != null;
	}
	
	public static class Builder {
		private User user;
		
		public Builder(String username, String password, Collection<? extends GrantedAuthority> authorities) {
			this.user = new User(username, password, authorities);
		}
		
		public User build() {
			return user;
		}
		
		public Builder firstName(String firstName) {
			user.setFirstName(firstName);
			return this;
		}
		
		public Builder lastName(String lastName) {
			user.setLastName(lastName);
			return this;
		}
		
		public Builder provider(Provider provider) {
			user.setProvider(provider);
			return this;
		}
		
		public Builder email(String email) {
			user.setEmail(email);
			return this;
		}
	}
}
