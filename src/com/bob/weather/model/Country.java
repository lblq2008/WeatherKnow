package com.bob.weather.model;

import java.io.Serializable;

public class Country implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String countryName;
	private String countryCode;
	private int cityId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public Country() {
		super();
	}

	public Country(String countryName, String countryCode, int cityId) {
		super();
		this.countryName = countryName;
		this.countryCode = countryCode;
		this.cityId = cityId;
	}

	public Country(int id, String countryName, String countryCode, int cityId) {
		super();
		this.id = id;
		this.countryName = countryName;
		this.countryCode = countryCode;
		this.cityId = cityId;
	}

}
