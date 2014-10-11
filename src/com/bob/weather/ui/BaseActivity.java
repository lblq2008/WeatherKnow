package com.bob.weather.ui;

import com.bob.weather.utils.ActivityCollector;

import android.app.Activity;
import android.os.Bundle;

/**
 * Activity基类，应用中所有的Activity都继承这个类
 * @ClassName BaseActivity
 * @author Bob
 * @date 2014-10-11 上午11:04:29 
 */
public class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActivityCollector.addActivity(this);//加入Activity队列
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ActivityCollector.removeActivity(this);//从Activity队列中移除
	}
}
