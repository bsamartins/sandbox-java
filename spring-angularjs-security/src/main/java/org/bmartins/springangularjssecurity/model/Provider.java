package org.bmartins.springangularjssecurity.model;

public enum Provider { 
	TWITTER("twitter"),
	GOOGLE("google"),
	LINKEDIN("linkedin"),
	GITHUB("github");
	
	private String providerId;

	private Provider(String providerId) {
		this.providerId = providerId;
	}
	
	String getProviderId() {
		return providerId;
	}

	public static Provider fromString(String providerId) {
		Provider provider = fromStringSafe(providerId);
		if(provider != null) {
			return provider;
		} else {
			throw new IllegalArgumentException("No match found for providerId: " + providerId);
		}		
	}

	private static Provider fromStringSafe(String providerId) {
		Provider result = null;
		for(Provider provider: values()) {
			if(provider.providerId.equals(providerId)) {
				result = provider;
				break;
			}
		}		
		return result;
	}

}