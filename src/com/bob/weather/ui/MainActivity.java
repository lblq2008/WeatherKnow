package com.bob.weather.ui;

import com.bob.weather.ui.R;

import android.content.Intent;
import android.os.Bundle;

/**
 * 主Activity
 * @ClassName MainActivity
 * @author Bob
 * @date 2014-10-11 上午11:17:37 
 */
public class MainActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = new Intent(this, ChoseCityActivity.class);
		startActivity(intent);
	}
}
