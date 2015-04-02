package org.bmartins.sandbox.springwebflow.flows.masterdetail;

import java.io.Serializable;


public class MasterDetailModel implements Serializable {
	
	private String selectedCountry;

	public String getSelectedCountry() {
		return selectedCountry;
	}

	public void setSelectedCountry(String selectedCountry) {
		this.selectedCountry = selectedCountry;
	}
	
}
