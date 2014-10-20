package com.bob.weather.db;

import java.util.ArrayList;
import java.util.List;

import com.bob.weather.model.City;
import com.bob.weather.model.Country;
import com.bob.weather.model.Province;
import com.bob.weather.model.WeatherInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 数据库表操作类
 * @ClassName DBTableService
 * @author Bob
 * @date 2014-10-13 上午10:38:49 
 */
public class DBTableService {

	private  DBHelper dbHelper = null;
	private static DBTableService dbService ;
	private static SQLiteDatabase db ;
	public DBTableService(Context context) {
		this.dbHelper = new DBHelper(context);
		db = dbHelper.getWritableDatabase();
	}
	
	public synchronized static DBTableService getInstance(Context context){
		if(dbService == null){
			dbService = new DBTableService(context);
		}
		return dbService ;
	}
	
	public void addWeatherInfo(WeatherInfo info){
		//SQLiteDatabase db = dbHelper.getWritableDatabase();
		deleteWeatherInfo(info);
		ContentValues values = new ContentValues();
		values.put("city_name", info.getCityName());
		values.put("weather_code", info.getWeatherCode());
		values.put("weather_info", info.getWeatherInfo());
		db.insert(DBHelper.WEATHERTABLE, null, values);
		if (db != null) {
			//db.close();
		}
	}
	
	public void updateWeatherInfo(WeatherInfo info){
//		if(deleteWeatherInfo(info)>0){
//			addWeatherInfo(info);
//			return ;
//		}
		//SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("city_name", info.getCityName());
		values.put("weather_code", info.getWeatherCode());
		values.put("weather_info", info.getWeatherInfo());
		db.update(DBHelper.WEATHERTABLE, values, " weather_code = ? ", new String[]{info.getWeatherCode()});
		if (db != null) {
			//db.close();
		}
	}
	
	public int deleteWeatherInfo(WeatherInfo info){
		//SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count = db.delete(DBHelper.WEATHERTABLE, " weather_code = ? ", new String[]{info.getWeatherCode()});
		if (db != null) {
			//db.close();
		}
		return count ;
	}
	
	public List<WeatherInfo> getWeatherInfos(){
		List<WeatherInfo> list = new ArrayList<WeatherInfo>();
		//SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(DBHelper.WEATHERTABLE, null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			WeatherInfo weatherInfo = new WeatherInfo();
			weatherInfo.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
			weatherInfo.setWeatherCode(cursor.getString(cursor.getColumnIndex("weather_code")));
			weatherInfo.setWeatherInfo(cursor.getString(cursor.getColumnIndex("weather_info")));
			list.add(weatherInfo);
		}
		if(cursor!=null){
			cursor.close();
		}
		if (db != null) {
			//db.close();
		}
		return list ;
	}

	/**
	 * 根据id删除省份
	 * @param province
	 * @return
	 * @date 2014-10-13 上午10:46:14
	 */
	public int deleteProvince(Province province){
		//SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count = db.delete(DBHelper.PROVINCETABLE, " id = ?  ", new String[]{province.getId() + ""});
		if (db != null) {
			//db.close();
		}
		return count ;
	}
	
	/**
	 * 更新省份数据
	 * @param province
	 * @return
	 * @date 2014-10-13 上午10:49:13
	 */
	public int updateProvince(Province province){
		//SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("province_name", province.getProvinceName());
		values.put("province_code", province.getProvinceCode());
		int count = db.update(DBHelper.PROVINCETABLE, values, " id = ? ", new String[]{province.getId() + ""});
		if (db != null) {
			//db.close();
		}
		return count ;
	}
	
	/**
	 * 添加省份数据
	 * @param province
	 * @date 2014-10-13 上午10:39:27
	 */
	public void addProvince(Province province) {
		//SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("province_name", province.getProvinceName());
		values.put("province_code", province.getProvinceCode());
		db.insert(DBHelper.PROVINCETABLE, null, values);
		if (db != null) {
			//db.close();
		}
	}
	
	/**
	 * 查询单个省份信息
	 * @param id
	 * @return
	 * @date 2014-10-13 上午10:55:54
	 */
	public Province getProvince(int id){
		//SQLiteDatabase db = dbHelper.getReadableDatabase();
		Province province = null;
		Cursor cursor = db.query(DBHelper.PROVINCETABLE, null, " id = ? ", new String[]{id + ""}, null, null, null);
		if(cursor.moveToFirst()){
			province = new Province();
			province.setId(id);
			province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
			province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
		}
		if(cursor != null){
			cursor.close();
		}
		if (db != null) {
			//db.close();
		}
		return province ;
	}
	
	/**
	 * 获取所有省份的信息
	 * @return
	 * @date 2014-10-13 上午10:59:38
	 */
	public List<Province> getAllProvinces(){
		List<Province> list = new ArrayList<Province>();
		//SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(DBHelper.PROVINCETABLE, null, null , null , null, null, null);
		while (cursor.moveToNext()) {
			Province province = new Province();
			province.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
			province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
			province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
			list.add(province);
		}
		if(cursor != null){
			cursor.close();
		}
		if (db != null) {
			//db.close();
		}
		return list ;
	}
	
	/**
	 * 添加城市数据
	 * @param city
	 * @date 2014-10-13 上午11:02:00
	 */
	public void addCity(City city){
		//SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("city_name", city.getCityName());
		values.put("city_code", city.getCityCode());
		values.put("province_id", city.getProvinceId());
		db.insert(DBHelper.CITYTABLE, null, values);
		if (db != null) {
			//db.close();
		}
	}
	
	/**
	 * 查询单个城市信息
	 * @param id
	 * @return
	 * @date 2014-10-13 上午10:55:54
	 */
	public City getCity(int id){
		//SQLiteDatabase db = dbHelper.getReadableDatabase();
		City city = null;
		Cursor cursor = db.query(DBHelper.CITYTABLE, null, " id = ? ", new String[]{id + ""}, null, null, null);
		if(cursor.moveToFirst()){
			city = new City();
			city.setId(id);
			city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
			city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
			city.setProvinceId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("province_id"))));
		}
		if(cursor != null){
			cursor.close();
		}
		if (db != null) {
			//db.close();
		}
		return city ;
	}
	
	/**
	 * 获取某一省份所有城市的信息
	 * @return
	 * @date 2014-10-13 上午10:59:38
	 */
	public List<City> getAllProvinceCities(int provinceId){
		List<City> list = new ArrayList<City>();
		//SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(DBHelper.CITYTABLE, null, " province_id = ? " , new String[]{provinceId + ""} , null, null, null);
		while (cursor.moveToNext()) {
			City city = new City();
			city.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
			city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
			city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
			city.setProvinceId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("province_id"))));
			list.add(city);
		}
		if(cursor != null){
			cursor.close();
		}
		if (db != null) {
			//db.close();
		}
		return list ;
	}
	
	/**
	 * 获取某一省份所有城市的信息
	 * @return
	 * @date 2014-10-13 上午10:59:38
	 */
	public List<City> getAllCities(){
		List<City> list = new ArrayList<City>();
		//SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(DBHelper.CITYTABLE, null, null , null , null, null, null);
		while (cursor.moveToNext()) {
			City city = new City();
			city.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
			city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
			city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
			city.setProvinceId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("province_id"))));
			list.add(city);
		}
		if(cursor != null){
			cursor.close();
		}
		if (db != null) {
			//db.close();
		}
		return list ;
	}
	
	/**
	 * 添加县城数据
	 * @param city
	 * @date 2014-10-13 上午11:02:00
	 */
	public void addCountry(Country country){
		//SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("country_name", country.getCountryName());
		values.put("country_code", country.getCountryCode());
		values.put("city_id", country.getCityId());
		db.insert(DBHelper.COUNTRYTABLE, null, values);
		if (db != null) {
			//db.close();
		}
	}
	
	/**
	 * 查询单个县区信息
	 * @param id
	 * @return
	 * @date 2014-10-13 上午10:55:54
	 */
	public Country getCountry(int id){
		///SQLiteDatabase db = dbHelper.getReadableDatabase();
		Country country = null;
		Cursor cursor = db.query(DBHelper.COUNTRYTABLE, null, " id = ? ", new String[]{id + ""}, null, null, null);
		if(cursor.moveToFirst()){
			country = new Country();
			country.setId(id);
			country.setCountryName(cursor.getString(cursor.getColumnIndex("country_name")));
			country.setCountryCode(cursor.getString(cursor.getColumnIndex("country_code")));
			country.setCityId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("city_id"))));
		}
		if(cursor != null){
			cursor.close();
		}
		if (db != null) {
			//db.close();
		}
		return country ;
	}
	
	/**
	 * 获取某一城市所有县区的信息
	 * @return
	 * @date 2014-10-13 上午10:59:38
	 */
	public List<Country> getAllCityCountries(int cityId){
		List<Country> list = new ArrayList<Country>();
		//SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(DBHelper.COUNTRYTABLE, null, " city_id = ? " , new String[]{cityId + ""} , null, null, null);
		while (cursor.moveToNext()) {
			Country country = new Country();
			country.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
			country.setCountryName(cursor.getString(cursor.getColumnIndex("country_name")));
			country.setCountryCode(cursor.getString(cursor.getColumnIndex("country_code")));
			country.setCityId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("city_id"))));
			list.add(country);
		}
		if(cursor != null){
			cursor.close();
		}
		if (db != null) {
			//db.close();
		}
		return list ;
	}
}
