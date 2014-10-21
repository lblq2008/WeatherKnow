package com.bob.weather.ui;

import com.bob.weather.sys.UpdateWeatherService;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;
import android.widget.Toast;

public class LaunchActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initViews();
	}
	View view ;
	private void initViews() {
		// TODO Auto-generated method stub
		view = View.inflate(this, R.layout.activity_launch, null);
		setContentView(view);
		animationLaunch(view);
		((TextView)view.findViewById(R.id.tv_app)).setText(getAppInfo());
	}

	/**
	 * 获取APP信息
	 * @return
	 * @date 2014-10-21 下午03:53:22
	 */
	private String getAppInfo(){
		String packageName = getApplicationContext().getPackageName();
		try {
			PackageInfo packageInfo = getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
			String versionName = packageInfo.versionName;
			return getString(R.string.app_name) + " : " + versionName ;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "" ;
	}
	
	private void animationLaunch(View view) {
		// TODO Auto-generated method stub
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
		alphaAnimation.setDuration(2000);
		view.setAnimation(alphaAnimation);
		alphaAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				Toast.makeText(LaunchActivity.this, "欢迎使用掌天气", Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Intent updateIntent = new Intent(LaunchActivity.this, UpdateWeatherService.class);
						startService(updateIntent);
					}
				}).start();
				Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
}
