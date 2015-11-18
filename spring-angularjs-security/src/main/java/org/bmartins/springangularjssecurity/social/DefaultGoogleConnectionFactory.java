package org.bmartins.springangularjssecurity.social;

import org.springframework.social.google.connect.GoogleConnectionFactory;

public class DefaultGoogleConnectionFactory extends GoogleConnectionFactory {

	public DefaultGoogleConnectionFactory(String clientId, String clientSecret) {
		super(clientId, clientSecret);
		setScope("profile");
	}
}
