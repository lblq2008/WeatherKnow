package com.bob.weather.utils;

import android.content.Context;

import com.bob.weather.config.Configs;
import com.bob.weather.ui.SystemApplication;

public class SharePreUtil {
	public static String getValue(String key) {
		return SystemApplication
				.getContext()
				.getSharedPreferences(Configs.SharePreferenceName,
						Context.MODE_PRIVATE)
				.getString(key, "3600000");
	}

	public static void setValue(String key, String value) {
		SystemApplication
				.getContext()
				.getSharedPreferences(Configs.SharePreferenceName,
						Context.MODE_PRIVATE).edit().putString(key, value)
				.commit();
	}
}
