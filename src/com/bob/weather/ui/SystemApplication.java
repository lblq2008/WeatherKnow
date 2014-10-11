package com.bob.weather.ui;

import android.app.Application;
import android.content.Context;

/**
 * 初始化系统的变量或方法
 * @ClassName SystemApplication
 * @author Bob
 * @date 2014-10-11 下午02:29:19 
 */
public class SystemApplication extends Application {
	
	private static Context context;//全局Context

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		context = getApplicationContext();
	}

	public static Context getContext() {
		return context;
	}
	
}
