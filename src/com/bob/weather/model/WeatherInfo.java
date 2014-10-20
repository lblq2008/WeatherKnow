package com.bob.weather.model;

import java.io.Serializable;

public class WeatherInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id ;
	private String cityName ;
	private String weatherCode ;
	private String weatherInfo ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getWeatherCode() {
		return weatherCode;
	}
	public void setWeatherCode(String weatherCode) {
		this.weatherCode = weatherCode;
	}
	public String getWeatherInfo() {
		return weatherInfo;
	}
	public void setWeatherInfo(String weatherInfo) {
		this.weatherInfo = weatherInfo;
	}
	public WeatherInfo(String cityName, String weatherCode, String weatherInfo) {
		super();
		this.cityName = cityName;
		this.weatherCode = weatherCode;
		this.weatherInfo = weatherInfo;
	}
	public WeatherInfo() {
		super();
	}
	
}
