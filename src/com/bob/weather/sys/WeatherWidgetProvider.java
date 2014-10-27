package com.bob.weather.sys;

import java.util.List;
import java.util.Map;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import com.bob.weather.config.Configs;
import com.bob.weather.db.DBTableService;
import com.bob.weather.ui.R;
import com.bob.weather.utils.Utility;

public class WeatherWidgetProvider extends AppWidgetProvider {
	
	public static final String UpdateAction = "com.bob.weather.sys.wwp";
	private static RemoteViews remoteViews;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		if (remoteViews == null) {
			remoteViews = new RemoteViews(context.getPackageName(), R.layout.weatherwidget_layout);
		}
		if (intent.getAction().equals(UpdateAction)) {
			List<Map<String, String>> maps = Utility.parseWeatherInfo(DBTableService.getInstance(context));
			if(maps.size()>0){
				int i = Configs.currentWeather % (maps.size());
				String content = maps.get(i).get("city") + "\n"
						+ maps.get(i).get("temp1") + "~" + maps.get(i).get("temp2")
						+ "\n" + maps.get(i).get("weather") ;
				remoteViews.setTextViewText(R.id.tv_content, content);
				Configs.currentWeather++;
			}
		}
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		int[] appIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, WeatherWidgetProvider.class));
		appWidgetManager.updateAppWidget(appIds, remoteViews);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		for (int i = 0; i < appWidgetIds.length; i++) {
			updateWidgets(context, appWidgetManager, appWidgetIds[i]);
		}
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
	}

	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
	}

	private void updateWidgets(Context context,
			AppWidgetManager appWidgetManager, int appWidgetId) {
		remoteViews = new RemoteViews(context.getPackageName(), R.layout.weatherwidget_layout);
		Intent intentClick = new Intent(UpdateAction);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentClick, 0);
		remoteViews.setOnClickPendingIntent(R.id.tv_content, pendingIntent);
		appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
	}
}
