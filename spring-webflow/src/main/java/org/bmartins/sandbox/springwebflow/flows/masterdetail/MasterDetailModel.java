package org.bmartins.sandbox.springwebflow.flows.masterdetail;

import java.io.Serializable;

import org.bmartins.sandbox.springwebflow.model.Country;


public class MasterDetailModel implements Serializable {
	
	private String selectedCountry;
	private Country country;

	public String getSelectedCountry() {
		return selectedCountry;
	}

	public void setSelectedCountry(String selectedCountry) {
		this.selectedCountry = selectedCountry;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
}
