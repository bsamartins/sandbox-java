package org.bmartins.sandbox.springwebflow.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Country implements Serializable {
	
	private String code;
	private String name;
	private String capital;
	private String currency;
	private String phoneCode;
	
	private Continent continent;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Continent getContinent() {
		return continent;
	}
	public void setContinent(Continent continent) {
		this.continent = continent;
	}	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	public String getCapital() {
		return capital;
	}
	public void setCapital(String capital) {
		this.capital = capital;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getPhoneCode() {
		return phoneCode;
	}
	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}
}
