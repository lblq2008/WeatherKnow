package com.bob.weather.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public class LaunchActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initViews();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		View view = View.inflate(this, R.layout.activity_launch, null);
		setContentView(view);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
		alphaAnimation.setDuration(2000);
		view.setAnimation(alphaAnimation);
		alphaAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
}
