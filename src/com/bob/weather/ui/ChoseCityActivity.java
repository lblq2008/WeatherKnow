package com.bob.weather.ui;

import java.util.List;

import com.bob.weather.config.Configs;
import com.bob.weather.db.DBTableService;
import com.bob.weather.inf.HttpCallBackListener;
import com.bob.weather.model.City;
import com.bob.weather.model.Province;
import com.bob.weather.utils.HttpUtils;
import com.bob.weather.utils.LogUtil;
import com.bob.weather.utils.Utility;

import android.R.color;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ChoseCityActivity extends BaseActivity {
	private List<Province> provinces;
	private List<City> cities;
	private DBTableService dbService = null;
	private ListView lv_province, lv_city;
	private ProvinceAdapter provincesAdapter;
	private ArrayAdapter<City> citiesAdapter;
	private ProgressDialog progressDialog;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				progressDialog.dismiss();
				Toast.makeText(ChoseCityActivity.this, "加载数据异常",
						Toast.LENGTH_SHORT).show();
				// finish();
				break;
			case 1:
				progressDialog.dismiss();
				provincesAdapter.refresh(provinces);
				if (provinces.size() > 0) {
					provincesAdapter.setSelectItem(0);
					provincesAdapter.notifyDataSetInvalidated();
					loadCity(provinces.get(0));
				} else {
					Toast.makeText(ChoseCityActivity.this, "无省份信息",
							Toast.LENGTH_SHORT).show();
				}
				break;
			case 2:
				progressDialog.dismiss();
				citiesAdapter = new ArrayAdapter<City>(ChoseCityActivity.this,
						android.R.layout.simple_list_item_single_choice, cities);
				lv_city.setAdapter(citiesAdapter);
				citiesAdapter.notifyDataSetChanged();
				if (cities.size() > 0) {
				} else {
					Toast.makeText(ChoseCityActivity.this, "无城市信息",
							Toast.LENGTH_SHORT).show();
				}
				break;

			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chose_city);
		initViews();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		dbService = DBTableService.getInstance(this);
		lv_province = (ListView) this.findViewById(R.id.lv_province);
		// lv_province.setOnItemClickListener(this);
		lv_province.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				provincesAdapter.setSelectItem(position);
				provincesAdapter.notifyDataSetInvalidated();
				loadCity(provinces.get(0));
				LogUtil.i("ChoseCityActivity", "省份:"+ provinces.get(position).getProvinceName());
//				for(int i=0;i<parent.getCount();i++){
//		            View v=parent.getChildAt(parent.getCount()-1-i);
//		            if (position == i) {
//		                v.setBackgroundColor(Color.RED);
//		            } else {
//		                v.setBackgroundColor(Color.TRANSPARENT);
//		            }
//		        }
			}
		});
		lv_city = (ListView) this.findViewById(R.id.lv_city);

		progressDialog = new ProgressDialog(this);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage("正在初始化数据,请稍后...");
		progressDialog.setCancelable(false);

		provinces = dbService.getAllProvinces();
		cities = dbService.getAllCities();

		provincesAdapter = new ProvinceAdapter(this, provinces);
		lv_province.setAdapter(provincesAdapter);

		citiesAdapter = new ArrayAdapter<City>(this,
				android.R.layout.simple_list_item_single_choice, cities);
		lv_city.setAdapter(citiesAdapter);

		if (!(provinces.size() > 0)) {
			loadProvinces();
		} else {
			provincesAdapter.setSelectItem(0);
			loadCity(provinces.get(0));
		}
	}

	private void loadProvinces() {
		// TODO Auto-generated method stub
		progressDialog.setMessage("正在加载省份数据...");
		HttpUtils.requestHttpGet(progressDialog, Configs.ProvincesUrl
				+ Configs.pxiel, new HttpCallBackListener() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				boolean flag = Utility.handleProvinceData(dbService, response);
				provinces = dbService.getAllProvinces();
				Message message = Message.obtain();
				message.what = 1;
				message.obj = flag;
				handler.sendMessage(message);
			}

			@Override
			public void onFailed(String message) {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(0);
			}
		});
	}

	private void loadCity(final Province province) {
		// TODO Auto-generated method stub
		cities = dbService.getAllProvinceCities(Integer.parseInt(province
				.getProvinceCode()));
		if (cities.size() > 0) {
			citiesAdapter = new ArrayAdapter<City>(ChoseCityActivity.this,
					android.R.layout.simple_list_item_single_choice, cities);
			lv_city.setAdapter(citiesAdapter);
			citiesAdapter.notifyDataSetChanged();
			return;
		}
		progressDialog.setMessage("正在加载城市数据...");
		HttpUtils.requestHttpGet(progressDialog, Configs.ProvincesUrl
				+ province.getProvinceCode() + Configs.pxiel,
				new HttpCallBackListener() {
					@Override
					public void onSuccess(String response) {
						// TODO Auto-generated method stub
						boolean flag = Utility.handleCityData(dbService,
								response,
								Integer.parseInt(province.getProvinceCode()));
						cities = dbService.getAllProvinceCities(Integer
								.parseInt(province.getProvinceCode()));
						Message message = Message.obtain();
						message.what = 2;
						message.obj = flag;
						handler.sendMessage(message);
					}

					@Override
					public void onFailed(String message) {
						// TODO Auto-generated method stub
						handler.sendEmptyMessage(0);
					}
				});
	}

	class ProvinceAdapter extends BaseAdapter {
		private Context context;
		private List<Province> pList;

		public ProvinceAdapter(Context context, List<Province> pList) {
			this.context = context;
			this.pList = pList;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return pList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return pList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null ;
			if(convertView == null){
				holder = new ViewHolder();
				convertView = View.inflate(context, R.layout.activity_chose_city_item, null);
				holder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv_name.setText(pList.get(position).getProvinceName());
			if(position == selectItem){
				holder.tv_name.setBackgroundColor(color.holo_blue_bright);
			}else{
				holder.tv_name.setBackgroundColor(color.holo_green_light);
			}
			return convertView;
		}

		public void setSelectItem(int selectItem) {
			this.selectItem = selectItem;
		}
		
		public void refresh(List<Province> pList){
			this.pList = pList ;
			notifyDataSetChanged();
		}
		private int selectItem = -1;

	}

	class ViewHolder {
		TextView tv_name;
	}
}
