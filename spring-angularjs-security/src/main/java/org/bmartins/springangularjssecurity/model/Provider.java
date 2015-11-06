package org.bmartins.springangularjssecurity.model;

public enum Provider { 
	TWITTER("twitter"); 
	
	private String providerId;

	private Provider(String providerId) {
		this.providerId = providerId;
	}
	
	String getProviderId() {
		return providerId;
	}

	public static Provider fromString(String providerId) {
		for(Provider provider: values()) {
			if(provider.providerId.equals(providerId)) {
				return provider;
			}
		}
		return null;
	}

}