package com.bob.weather.ui;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.bob.weather.db.DBTableService;
import com.bob.weather.model.WeatherInfo;

public class SelectedActivity extends BaseActivity {
	
	private GridView gv_cities;
	private SelectedCityAdapter adapter;
	private List<WeatherInfo> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selected_city);
		initViews();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		if(actionBar != null){
			actionBar.setTitle("已选城市");
		}
		gv_cities = (GridView) this.findViewById(R.id.gv_cities);
		list = DBTableService.getInstance(this).getWeatherInfos();
		list.add(new WeatherInfo("+", "", ""));
		adapter = new SelectedCityAdapter(this, list);
		gv_cities.setAdapter(adapter);
		gv_cities.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				if (position == (list.size() - 1)) {
					Intent intent = new Intent(SelectedActivity.this,ChoseCityActivity.class);
					startActivity(intent);
				}
			}
		});

		gv_cities.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				if (position < list.size() - 1) {
					deleteSelectedDialog(list.get(position));
				}
				return false;
			}
		});
	}

	private void deleteSelectedDialog(final WeatherInfo info) {
		new AlertDialog.Builder(this).setTitle("温馨提示")
				.setMessage("您确定要删除 " + info.getCityName() + " 吗?")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						DBTableService.getInstance(SelectedActivity.this).deleteWeatherInfo(info);
						refreshData();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				}).setCancelable(false).create().show();
	}
	
	private void refreshData(){
		list = DBTableService.getInstance(this).getWeatherInfos();
		list.add(new WeatherInfo("+", "", ""));
		adapter.refresh(list);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		refreshData();
	}

	public class SelectedCityAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private List<WeatherInfo> mList;

		public SelectedCityAdapter(Context mContext, List<WeatherInfo> mList) {
			this.mList = mList;
			mInflater = LayoutInflater.from(mContext);
		}

		public void refresh(List<WeatherInfo> mList) {
			this.mList = mList;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(
						R.layout.activity_selected_city_item, null);
				holder.tv_city_name = (TextView) convertView
						.findViewById(R.id.tv_city_name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv_city_name.setText(mList.get(position).getCityName());
			return convertView;
		}
	}

	class ViewHolder {
		TextView tv_city_name;
	}
}
