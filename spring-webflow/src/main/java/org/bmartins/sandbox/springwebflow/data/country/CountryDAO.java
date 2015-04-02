package org.bmartins.sandbox.springwebflow.data.country;

import java.util.List;

import org.bmartins.sandbox.springwebflow.model.Continent;
import org.bmartins.sandbox.springwebflow.model.Country;

public interface CountryDAO {
	
	public List<Country> listCountries();
	
	public Country getCountry(String code);
	public Continent getContinent(String code);

}
