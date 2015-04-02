package org.bmartins.sandbox.springwebflow.flows.masterdetail;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bmartins.sandbox.springwebflow.data.country.CountryDAO;
import org.bmartins.sandbox.springwebflow.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MasterDetailAction {
	
	@Autowired
	private CountryDAO countryDAO;
	
	public MasterDetailModel init() {
		MasterDetailModel model = new MasterDetailModel();
		return model;
	}
	
	public List<Country> getCountries() {
		return countryDAO.listCountries();
	}
	
	public void onCountrySelected(MasterDetailModel model) {
		if(StringUtils.isNotBlank(model.getSelectedCountry())) {
			Country country = countryDAO.getCountry(model.getSelectedCountry());
			model.setCountry(country);			
		}
	}

	
}
