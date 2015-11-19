package org.bmartins.springangularjssecurity.social;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.connect.LinkedInServiceProvider;

public class DefaultLinkedInConnectionFactory extends OAuth2ConnectionFactory<LinkedIn> {

	public DefaultLinkedInConnectionFactory(String consumerKey, String consumerSecret) {
		super("linkedin", new LinkedInServiceProvider(consumerKey, consumerSecret), new CustomLinkedInAdapter());
	}

}
