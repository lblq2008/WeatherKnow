package com.bob.weather.sys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
	public static boolean flag = true ;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if(flag){
			Intent i = new Intent(context, UpdateWeatherService.class);
			context.startService(i);
		}
	}

}
