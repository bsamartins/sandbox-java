package org.bmartins.springangularjssecurity.social;

import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.linkedin.connect.LinkedInAdapter;

public class CustomLinkedInAdapter extends LinkedInAdapter {

	@Override
	public UserProfile fetchUserProfile(LinkedIn linkedin) {
		LinkedInProfile linkedinProfile = linkedin.profileOperations().getUserProfile();
		return new UserProfileBuilder()
				.setName(linkedinProfile.getFirstName() + " " + linkedinProfile.getLastName())
				.setEmail(linkedinProfile.getEmailAddress())
				.setFirstName(linkedinProfile.getFirstName())
				.setLastName(linkedinProfile.getLastName())			
				.setUsername(linkedinProfile.getId())
				.build();
	}

}
