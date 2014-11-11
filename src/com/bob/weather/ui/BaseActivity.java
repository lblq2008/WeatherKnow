package com.bob.weather.ui;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.bob.weather.utils.ActivityCollector;
import com.bob.weather.utils.LogUtil;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.Window;

/**
 * Activity基类，应用中所有的Activity都继承这个类
 * @ClassName BaseActivity
 * @author Bob
 * @date 2014-10-11 上午11:04:29 
 */
public class BaseActivity extends Activity {
	ActionBar actionBar = null;
	private Context context ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this ;
		LogUtil.i("WeatherKnow", getClass().getSimpleName());
		ActivityCollector.addActivity(this);//加入Activity队列
		actionBar = getActionBar();
		if(actionBar != null){
			if(context instanceof MainActivity){
				actionBar.setDisplayHomeAsUpEnabled(false);
			}else{
				actionBar.setDisplayHomeAsUpEnabled(true);
			}
		}
		setOverflowShowingAlways(); 
	}
	
	/**
	 * 利用反射机制将类ViewConfiguration中变量sHasPermanentMenuKey修改成 false
	 * @date 2014-10-21 下午03:25:16
	 */
	private void setOverflowShowingAlways() {
		// TODO Auto-generated method stub
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		// TODO Auto-generated method stub
		if(featureId == Window.FEATURE_ACTION_BAR && menu != null){
			if(menu.getClass().getSimpleName().equals("MenuBuilder")){
				try {
					//让menu里面显示出图片
					Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ActivityCollector.removeActivity(this);//从Activity队列中移除
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
