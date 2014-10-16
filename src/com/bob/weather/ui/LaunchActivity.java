package com.bob.weather.ui;

import java.util.List;

import com.bob.weather.config.Configs;
import com.bob.weather.db.DBTableService;
import com.bob.weather.inf.HttpCallBackListener;
import com.bob.weather.model.City;
import com.bob.weather.model.Province;
import com.bob.weather.utils.HttpUtils;
import com.bob.weather.utils.Utility;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Toast;

public class LaunchActivity extends BaseActivity {
	private ProgressDialog progressDialog ;
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
		progressDialog = new ProgressDialog(this);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage("正在初始化数据,请稍后...");
		progressDialog.setCancelable(false);
		getProvinceFormNet();
	}

	private DBTableService dbService = null;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				Toast.makeText(LaunchActivity.this, "加载数据异常", Toast.LENGTH_SHORT).show();
				finish();
				break;
			case 1:
				getCitiesFromNet(Integer.parseInt(provinces.get(pCount).getProvinceCode()));
				pCount ++ ;
				if(pCount == provinces.size()){
					cities = dbService.getAllCities();
					Message message = Message.obtain();
					message.what = 2 ;
					handler.sendMessage(message);
				}
				break;
			case 2:
				getCountiesFromNet(Integer.parseInt(cities.get(cCount).getCityCode()));
				cCount ++ ;
				if(cCount == cities.size()){
					//progressDialog.dismiss();
					animationLaunch(view);
				}
				break;

			default:
				break;
			}
		}
	};
	int pCount = 0 ;
	int cCount = 0 ;
	List<Province> provinces;
	List<City> cities;
	private void getProvinceFormNet() {
		dbService = DBTableService.getInstance(this);
		provinces = dbService.getAllProvinces() ;
		cities = dbService.getAllCities();
		if (!(provinces.size() > 0)) {
			//progressDialog.show();
			HttpUtils.requestHttpGet(null, Configs.ProvincesUrl + Configs.pxiel,
					new HttpCallBackListener() {
						@Override
						public void onSuccess(String response) {
							// TODO Auto-generated method stub
							boolean flag = Utility.handleProvinceData(
									dbService, response);
							provinces = dbService.getAllProvinces() ;
							Message message = Message.obtain();
							message.what = 1 ;
							message.obj = flag ;
							handler.sendMessage(message);
						}

						@Override
						public void onFailed(String message) {
							// TODO Auto-generated method stub
							handler.sendEmptyMessage(0);
						}
					});
		}else{
			animationLaunch(view);
		}
	}
	
	private void getCitiesFromNet(final int provinceId){
		
		//if(!(cities.size()>0)){
			HttpUtils.requestHttpGet(null, Configs.ProvincesUrl + provinceId + Configs.pxiel,
					new HttpCallBackListener() {
						@Override
						public void onSuccess(String response) {
							// TODO Auto-generated method stub
							boolean flag = Utility.handleCityData(null, response, provinceId);
							Message message = Message.obtain();
							message.what = 1 ;
							message.obj = flag ;
							handler.sendMessage(message);
						}

						@Override
						public void onFailed(String message) {
							// TODO Auto-generated method stub
							handler.sendEmptyMessage(0);
						}
					});
		//}
	}
	
	private void getCountiesFromNet(final int cityId){
		//if(!(cities.size()>0)){
			HttpUtils.requestHttpGet(null, Configs.ProvincesUrl + cityId + Configs.pxiel,
					new HttpCallBackListener() {
						@Override
						public void onSuccess(String response) {
							// TODO Auto-generated method stub
							boolean flag = Utility.handleCountryData(dbService, response, cityId);
							Message message = Message.obtain();
							message.what = 2 ;
							message.obj = flag ;
							handler.sendMessage(message);
						}

						@Override
						public void onFailed(String message) {
							// TODO Auto-generated method stub
							handler.sendEmptyMessage(0);
						}
					});
		//}
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
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LaunchActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
}
