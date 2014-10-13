package com.bob.weather.utils;

import android.text.TextUtils;

import com.bob.weather.db.DBTableService;
import com.bob.weather.model.City;
import com.bob.weather.model.Country;
import com.bob.weather.model.Province;

/**
 * 数据操作类
 * @author Bob
 * @date 2014-10-13 下午8:58:40
 *
 */
public class Utility {
	/**
	 * 处理返回的省份数据
	 * @author Bob
	 * @date 2014-10-13 2014-10-13
	 * @param dbService
	 * @param response
	 * @return
	 */
	public static boolean handleProvinceData(DBTableService dbService,
			String response) {
		if (!TextUtils.isEmpty(response)) {
			String[] allProvinces = response.split(",");
			for (String p : allProvinces) {
				String[] array = p.split("\\|");
				Province province = new Province(array[1], array[0]);
				dbService.addProvince(province);//插入数据库
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 处理返回的市数据
	 * @author Bob
	 * @date 2014-10-13 2014-10-13
	 * @param dbService
	 * @param response
	 * @param provinceId
	 * @return
	 */
	public static boolean handleCityData(DBTableService dbService,
			String response , int provinceId){
		if(!TextUtils.isEmpty(response)){
			String[] allCities = response.split(",");
			for (String c:allCities) {
				String[] array = c.split("\\|");
				City city = new City(array[1], array[0], provinceId); 
				dbService.addCity(city);
			}
			return true ;
		}
		return false;
	}
	
	/**
	 * 处理返回的县区数据
	 * @author Bob
	 * @date 2014-10-13 2014-10-13
	 * @param dbService
	 * @param response
	 * @param cityId
	 * @return
	 */
	public static boolean handleCountryData(DBTableService dbService,
			String response , int cityId){
		if(!TextUtils.isEmpty(response)){
			String[] allCounties = response.split(",");
			for (String c:allCounties) {
				String[] array = c.split("\\|");
				Country country = new Country(array[1], array[0], cityId); 
				dbService.addCountry(country);
			}
			return true ;
		}
		return false;
	}
}
