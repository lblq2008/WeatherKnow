package com.bob.weather.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bob.weather.db.DBTableService;
import com.bob.weather.ui.R;
import com.bob.weather.utils.LogUtil;
import com.bob.weather.utils.Utility;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 主Activity
 * @ClassName MainActivity
 * @author Bob
 * @date 2014-10-11 上午11:17:37 
 */
public class MainActivity extends BaseActivity {
	private ViewPager vp_infos ;
	private List<View> list ;
	private WeatherVPAdapter adapter ;
	String infos = "{\"weatherinfo\":{\"city\":\"昆山\",\"cityid\":\"101190404\",\"temp1\":\"25℃\",\"temp2\":\"17℃\",\"weather\":\"多云\",\"img1\":\"d1.gif\",\"img2\":\"n1.gif\",\"ptime\":\"11:00\"}}" ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
	}
	private void initViews() {
		// TODO Auto-generated method stub
		vp_infos = (ViewPager)this.findViewById(R.id.vp_infos);
		list = getListViews();
		adapter = new WeatherVPAdapter(list);
		vp_infos.setAdapter(adapter);
		if(!(DBTableService.getInstance(this).getWeatherInfos().size()>0)){
			Intent intent = new Intent(this, ChoseCityActivity.class);
			startActivity(intent);
		}
	}
	
	private List<View> getListViews() {
		// TODO Auto-generated method stub
		List<View> listViews = new ArrayList<View>();
		List<Map<String,String>> maps = Utility.parseWeatherInfo(DBTableService.getInstance(this));
		LogUtil.i("MainActivity", "展示天气数：" +  maps.size());
		for (int i = 0; i < maps.size(); i++) {
			Map<String,String> map = maps.get(i);
			if(map != null){
				View info_view = View.inflate(this, R.layout.activity_main_vp_item, null);
				((TextView)info_view.findViewById(R.id.tv_city)).setText(map.get("city"));
				((TextView)info_view.findViewById(R.id.tv_temp1)).setText(map.get("temp1"));
				((TextView)info_view.findViewById(R.id.tv_temp2)).setText(map.get("temp2"));
				((TextView)info_view.findViewById(R.id.tv_weather)).setText(map.get("weather"));
				((TextView)info_view.findViewById(R.id.tv_ptime)).setText(map.get("ptime"));
				listViews.add(info_view);
			}
		}
		return listViews;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		adapter.refresh(getListViews());
	}

	class WeatherVPAdapter extends PagerAdapter{
		private List<View> mList ;
		
		public WeatherVPAdapter(List<View> list){
			this.mList = list ;
		}
		
		public void refresh(List<View> list){
			this.mList = list ;
			notifyDataSetChanged();
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mList.size();
		}

		@Override
		public boolean isViewFromObject(View v, Object object) {
			// TODO Auto-generated method stub
			return v == object;
		}
		
		@Override
		public Object instantiateItem(View container, int position) {
			// TODO Auto-generated method stub
			((ViewGroup) container).addView(mList.get(position), 0);
			return mList.get(position);
		}
		
		@Override
		public void destroyItem(View container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager) container).removeView(mList.get(position));
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_add_city:
			Intent intent = new Intent(this, ChoseCityActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
