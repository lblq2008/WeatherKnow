package com.bob.weather.model;

import java.io.Serializable;

/**
 * 城市实体类
 * @author Bob
 * @date 2014-10-12 下午3:57:12
 *
 */
public class City implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String cityName;
	private String cityCode;
	private int provinceId;

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

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public City() {
		super();
	}

	public City(String cityName, String cityCode, int provinceId) {
		super();
		this.cityName = cityName;
		this.cityCode = cityCode;
		this.provinceId = provinceId;
	}

	public City(int id, String cityName, String cityCode, int provinceId) {
		super();
		this.id = id;
		this.cityName = cityName;
		this.cityCode = cityCode;
		this.provinceId = provinceId;
	}

	@Override
	public String toString() {
		return cityName;
	}
	
}
