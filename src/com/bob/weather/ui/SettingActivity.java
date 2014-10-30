package com.bob.weather.ui;

import com.bob.weather.config.Configs;
import com.bob.weather.utils.SharePreUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingActivity extends BaseActivity {
	
	private Spinner sp_update_time;
	private String[] updateTimes = { "10分钟", "30分钟", "1小时", "6小时", "12小时",
			"24小时", "48小时" };
	private int minute = 60 * 1000;
	private int hour = 60 * 60 * 1000;
	private int[] updateTimeSeconds = { 10 * minute, 30 * minute, 1 * hour,
			6 * hour, 12 * hour, 24 * hour, 48 * hour };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		initViews();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		if (actionBar != null) {
			actionBar.setTitle("设置");
		}
		sp_update_time = (Spinner) this.findViewById(R.id.sp_update_time);
		sp_update_time.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, updateTimes));
		sp_update_time.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				SharePreUtil.setValue(Configs.UpdateTimeKey,
						String.valueOf(updateTimeSeconds[position]));
				Toast.makeText(SettingActivity.this, "更新时间间隔 " + updateTimes[position], Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
}
