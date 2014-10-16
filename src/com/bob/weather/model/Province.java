package com.bob.weather.model;

import java.io.Serializable;

public class Province implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id ;
	private String provinceName ;
	private String provinceCode ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public Province() {
		super();
	}
	public Province(String provinceName, String provinceCode) {
		super();
		this.provinceName = provinceName;
		this.provinceCode = provinceCode;
	}
	public Province(int id, String provinceName, String provinceCode) {
		super();
		this.id = id;
		this.provinceName = provinceName;
		this.provinceCode = provinceCode;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return provinceName;
	}
}
