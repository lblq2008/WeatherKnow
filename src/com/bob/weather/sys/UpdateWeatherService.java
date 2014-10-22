package com.bob.weather.sys;

import java.util.List;

import com.bob.weather.config.Configs;
import com.bob.weather.db.DBTableService;
import com.bob.weather.inf.HttpCallBackListener;
import com.bob.weather.model.WeatherInfo;
import com.bob.weather.utils.HttpUtils;
import com.bob.weather.utils.LogUtil;
import com.bob.weather.utils.Utility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

public class UpdateWeatherService extends Service {
	public static boolean isChange = true;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		List<WeatherInfo> list = DBTableService.getInstance(this).getWeatherInfos();
		LogUtil.i("UpdateWeatherService", "城市数量："+ list.size());
		for (int i = 0; i < list.size(); i++) {
			String url = Configs.WeatherInfoUrl + list.get(i).getWeatherCode() + Configs.html;
			HttpUtils.requestHttpGet(null, url, new HttpCallBackListener() {
				@Override
				public void onSuccess(String response) {
					// TODO Auto-generated method stub
					Utility.handleWeatherInfo(DBTableService.getInstance(UpdateWeatherService.this), response);
				}
				
				@Override
				public void onFailed(String message) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		int anHour = 60*60*1000 ;
		long triggerTime = SystemClock.elapsedRealtime() + anHour ;
		Intent i = new Intent(this, AlarmReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
		alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pi);
		return super.onStartCommand(intent, flags, startId);
	}
}
