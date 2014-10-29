package com.bob.weather.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingActivity extends BaseActivity {
	private Spinner sp_update_time ;
	private String[] updateTimes = {"10分钟","30分钟","1小时","6小时","12小时","24小时","48小时"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		initViews();
	}
	private void initViews() {
		// TODO Auto-generated method stub
		if(actionBar != null){
			actionBar.setTitle("设置");
		}
		sp_update_time = (Spinner)this.findViewById(R.id.sp_update_time);
		sp_update_time.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, updateTimes));
	}
}
