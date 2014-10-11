package com.bob.weather.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

/**
 * Activity管理类
 * @ClassName ActivityCollector
 * @author Bob
 * @date 2014-10-11 上午11:01:58 
 */
public class ActivityCollector {
	//装载所有Activity的列表
	public static List<Activity> activities = new ArrayList<Activity>();

	/**
	 * 添加一个Activity
	 * @param activity
	 * @date 2014-10-11 上午11:02:39
	 */
	public static void addActivity(Activity activity) {
		activities.add(activity);
	}
	
	/**
	 * 移除一个Activity
	 * @param activity
	 * @date 2014-10-11 上午11:03:00
	 */
	public static void removeActivity(Activity activity) {
		activities.remove(activity);
	}
	
	/**
	 * 结束所有的Activity
	 * @date 2014-10-11 上午11:03:20
	 */
	public static void finishAllActivities(){
		for (Activity activity:activities) {
			if(!activity.isFinishing()){
				activity.finish();
			}
		}
	}
	
}
