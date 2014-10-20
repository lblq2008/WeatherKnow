package com.bob.weather.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.bob.weather.db.DBTableService;
import com.bob.weather.model.City;
import com.bob.weather.model.Country;
import com.bob.weather.model.Province;
import com.bob.weather.model.WeatherInfo;

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
	//{"weatherinfo":{"city":"昆山","cityid":"101190404","temp1":"25℃","temp2":"17℃","weather":"多云","img1":"d1.gif","img2":"n1.gif","ptime":"11:00"}}
	/**
	 * 解析天气信息
	 * @param json
	 * @return
	 * @date 2014-10-20 上午11:13:40
	 */
	public static List<Map<String,String>> parseWeatherInfo(DBTableService dbService){
		if(dbService == null){
			return null ;
		}
		List<WeatherInfo> list = dbService.getWeatherInfos();
		List<Map<String,String>> maps = new ArrayList<Map<String,String>>();
		for (int i = 0; i < list.size(); i++) {
			try {
				Map<String,String> map = new HashMap<String, String>();
				JSONObject jsonObject = new JSONObject(list.get(i).getWeatherInfo()).getJSONObject("weatherinfo");
				map.put("city", jsonObject.getString("city"));
				map.put("temp1", jsonObject.getString("temp1"));
				map.put("temp2", jsonObject.getString("temp2"));
				map.put("weather", jsonObject.getString("weather"));
				map.put("ptime", jsonObject.getString("ptime"));
				maps.add(map);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return maps ;
	}
	
	public static void handleWeatherInfo(DBTableService dbService , String response){
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(response).getJSONObject("weatherinfo");
			String cityName = jsonObject.getString("city");
			String weatherCode = jsonObject.getString("cityid");
			dbService.addWeatherInfo(new WeatherInfo(cityName, weatherCode, response));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 }
