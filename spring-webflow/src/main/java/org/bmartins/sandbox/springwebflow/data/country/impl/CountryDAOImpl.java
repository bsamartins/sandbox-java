package org.bmartins.sandbox.springwebflow.data.country.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bmartins.sandbox.springwebflow.data.country.CountryDAO;
import org.bmartins.sandbox.springwebflow.model.Continent;
import org.bmartins.sandbox.springwebflow.model.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class CountryDAOImpl implements CountryDAO, InitializingBean {

	private static final String DATA_COUNTRIES_CONTINENT_JSON = "/data/countries/continent.json";
	private static final String DATA_COUNTRIES_CAPITAL_JSON = "/data/countries/capital.json";
	private static final String DATA_COUNTRIES_CURRENCY_JSON = "/data/countries/currency.json";
	private static final String DATA_COUNTRIES_NAMES_JSON = "/data/countries/names.json";
	private static final String DATA_COUNTRIES_PHONE_JSON = "/data/countries/phone.json";

	private static final Logger LOG = LoggerFactory.getLogger(CountryDAOImpl.class);
	
	private List<Country> countries = new ArrayList<>();
	private List<Continent> continents = new ArrayList<>();
	
	@Override
	public void afterPropertiesSet() throws Exception {				
		loadCountryDetails();
		loadContinents();
		
		LOG.trace("Countries: {}", countries);
	}

	@SuppressWarnings("unchecked")
	private void loadCountryDetails() throws Exception {
		Resource countryNamesJson = new ClassPathResource(DATA_COUNTRIES_NAMES_JSON);
		Resource countryCapitalsJson = new ClassPathResource(DATA_COUNTRIES_CAPITAL_JSON);
		Resource countryCurrenciesJson = new ClassPathResource(DATA_COUNTRIES_CURRENCY_JSON);
		Resource countryPhoneJson = new ClassPathResource(DATA_COUNTRIES_PHONE_JSON);
				
		Map<String, String> countryMap = new ObjectMapper().readValue(countryNamesJson.getInputStream(), Map.class);
		Map<String, String> countryCapitalsMap = new ObjectMapper().readValue(countryCapitalsJson.getInputStream(), Map.class);
		Map<String, String> countryCurrenciesMap = new ObjectMapper().readValue(countryCurrenciesJson.getInputStream(), Map.class);
		Map<String, String> countryPhonesMap = new ObjectMapper().readValue(countryPhoneJson.getInputStream(), Map.class);
		
		countryMap.forEach((key, value) ->{
			Country country = new Country();
			country.setCode(key);
			country.setName(value);
			country.setCapital(countryCapitalsMap.get(key));
			country.setCurrency(countryCurrenciesMap.get(key));
			country.setPhoneCode(countryPhonesMap.get(key));
			
			countries.add(country);
		});
		
		countries.sort((c1, c2) -> c1.getCode().compareTo(c2.getCode()));
	}

	@SuppressWarnings("unchecked")
	private void loadContinents() throws Exception {
		Resource continentResource = new ClassPathResource(DATA_COUNTRIES_CONTINENT_JSON);				
		Map<String, String> continentMap = new ObjectMapper().readValue(continentResource.getInputStream(), Map.class);
		
		continents = continentMap.values().stream()
			.distinct()
			.map(c -> {
				Continent continent = new Continent();
				continent.setCode(c);
				return continent;
			})
			.collect(Collectors.toList());
		
		continentMap.entrySet().stream()
			.collect(Collectors.groupingBy(t -> t.getValue()))
			.forEach((continentCode,countrySet) -> {
				Continent continent = getContinent(continentCode);
				countrySet.stream()
					.forEach(entry -> {
						Country country = getCountry(entry.getKey());
						country.setContinent(continent);
						continent.getCountries().add(country);
					});				
			});
	}
	
	@Override
	public Country getCountry(String code) {
		return countries.stream().filter(x -> x.getCode().equals(code)).findFirst().get();
	}

	@Override
	public Continent getContinent(String code) {
		return continents.stream().filter(x -> x.getCode().equals(code)).findFirst().get();
	}

	@Override
	public List<Country> listCountries() {
		return Collections.unmodifiableList(countries);
	}
}
