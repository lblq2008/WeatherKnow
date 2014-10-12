package com.bob.weather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库操作类
 * @author Bob
 * @date 2014-10-12 下午3:55:48
 *
 */
public class DBHelper extends SQLiteOpenHelper {
	public static final String DBNAME = "weather.db";//数据库名称
	public static final int DBVERSION = 1;//数据库版本号
	public static final String PROVINCETABLE = "province";//省份表名称
	public static final String CITYTABLE = "city";//城市表名称
	public static final String COUNTRYTABLE = "country";//县城表名称
	public static final String CREATE_PROVINCE = "create table "
			+ PROVINCETABLE + " (id integer primary key autoincrement, "
			+ "province_name text, " 
			+ "province_code text) ";
	public static final String CREATE_CITY = "create table " + CITYTABLE
			+ " (id integer primary key autoincrement, " 
			+ "city_name text, "
			+ "city_code text, " 
			+ "province_id integer) ";
	public static final String CREATE_COUNTRY = "create table " + COUNTRYTABLE
			+ " (id integer primary key autoincrement, "
			+ "country_name text, " 
			+ "country_code text, "
			+ "city_id integer) ";

	public DBHelper(Context context) {
		super(context, DBNAME, null, DBVERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_PROVINCE);//创建省份表
		db.execSQL(CREATE_CITY);//创建城市表
		db.execSQL(CREATE_COUNTRY);//创建县城表
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
