package com.bob.weather.ui;

import java.util.ArrayList;
import java.util.List;

import com.bob.weather.config.Configs;
import com.bob.weather.db.DBTableService;
import com.bob.weather.inf.HttpCallBackListener;
import com.bob.weather.model.City;
import com.bob.weather.model.Country;
import com.bob.weather.model.Province;
import com.bob.weather.utils.HttpUtils;
import com.bob.weather.utils.LogUtil;
import com.bob.weather.utils.Utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 城市选择页面
 * @ClassName ChoseCityActivity
 * @author Bob
 * @date 2014-10-17 下午03:24:02 
 */
public class ChoseCityActivity extends BaseActivity {
	
	private List<Province> provinces;
	private List<City> cities;
	private List<Country> countries;
	private DBTableService dbService = null;
	private ListView lv_province, lv_city, lv_country;
	private ProvinceAdapter provincesAdapter;
	private CityAdapter citiesAdapter;
	private CountryAdapter countriesAdapter;
	private ProgressDialog progressDialog;
	private TextView tv_province ,tv_city,tv_country ;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				progressDialog.dismiss();
				Toast.makeText(ChoseCityActivity.this, "加载数据异常",Toast.LENGTH_SHORT).show();
				finish();
				break;
			case 1:
				progressDialog.dismiss();
				provinces = dbService.getAllProvinces();
				provincesAdapter.refresh(provinces);
				if (provinces.size() > 0) {
					provincesAdapter.setSelectItem(0);
					loadCity(provinces.get(0));
				} else {
					Toast.makeText(ChoseCityActivity.this, "无省份信息",Toast.LENGTH_SHORT).show();
				}
				break;
			case 2:
				progressDialog.dismiss();
				if (cities.size() > 0) {
					citiesAdapter.refresh(cities);
					citiesAdapter.setSelectItem(0);
					loadCountry(cities.get(0));
				} else {
					Toast.makeText(ChoseCityActivity.this, "无城市信息",Toast.LENGTH_SHORT).show();
				}
				break;
			case 3:
				progressDialog.dismiss();
				if (countries.size() > 0) {
					countriesAdapter.refresh(countries);
					countriesAdapter.setSelectItem(0);
				} else {
					Toast.makeText(ChoseCityActivity.this, "无县城信息",Toast.LENGTH_SHORT).show();
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
		progressDialog = new ProgressDialog(this);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage("正在初始化数据,请稍后...");
		progressDialog.setCancelable(false);
		tv_province = (TextView)this.findViewById(R.id.tv_province);
		tv_city = (TextView)this.findViewById(R.id.tv_city);
		tv_country = (TextView)this.findViewById(R.id.tv_country);
		lv_province = (ListView) this.findViewById(R.id.lv_province);
		lv_province.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				provincesAdapter.setSelectItem(position);
				loadCity(provinces.get(position));
				LogUtil.i("ChoseCityActivity", "省份:"+ provinces.get(position).getProvinceName());
			}
		});
		lv_city = (ListView) this.findViewById(R.id.lv_city);
		lv_city.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				// TODO Auto-generated method stub
				citiesAdapter.setSelectItem(position);
				loadCountry(cities.get(position));
				LogUtil.i("ChoseCityActivity", "城市:"+ cities.get(position).getCityName());
			}
		});
		lv_country = (ListView) this.findViewById(R.id.lv_country);
		lv_country.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				// TODO Auto-generated method stub
				countriesAdapter.setSelectItem(position);
			}
		});

		provinces = dbService.getAllProvinces();
		cities = new ArrayList<City>();
		countries = new ArrayList<Country>();
		provincesAdapter = new ProvinceAdapter(this, provinces);
		lv_province.setAdapter(provincesAdapter);
		citiesAdapter = new CityAdapter(this, cities);
		lv_city.setAdapter(citiesAdapter);
		countriesAdapter = new CountryAdapter(this, countries);
		lv_country.setAdapter(countriesAdapter);

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
				Utility.handleProvinceData(dbService, response);
				handler.sendEmptyMessage(1);
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
		cities.clear();
		cities = dbService.getAllProvinceCities(province.getId());
		if (cities.size() > 0) {
			citiesAdapter.refresh(cities);
			citiesAdapter.setSelectItem(0);
			loadCountry(cities.get(0));
			return;
		}
		progressDialog.setMessage("正在加载城市数据...");
		HttpUtils.requestHttpGet(progressDialog, Configs.ProvincesUrl + province.getProvinceCode() + Configs.pxiel,
				new HttpCallBackListener() {
					@Override
					public void onSuccess(String response) {
						// TODO Auto-generated method stub
						Utility.handleCityData(dbService,response, province.getId());
						cities = dbService.getAllProvinceCities(province.getId());
						handler.sendEmptyMessage(2);
					}
					@Override
					public void onFailed(String message) {
						// TODO Auto-generated method stub
						handler.sendEmptyMessage(0);
					}
				});
	}

	private void loadCountry(final City city) {
		countries.clear();
		countries = dbService.getAllCityCountries(city.getId());
		if (countries.size() > 0) {
			countriesAdapter.refresh(countries);
			countriesAdapter.setSelectItem(0);
			return;
		}
		progressDialog.setMessage("正在加载县城数据...");
		HttpUtils.requestHttpGet(progressDialog,Configs.ProvincesUrl + city.getCityCode() + Configs.pxiel,
				new HttpCallBackListener() {
					@Override
					public void onSuccess(String response) {
						// TODO Auto-generated method stub
						Utility.handleCountryData(dbService, response , city.getId());
						countries = dbService.getAllCityCountries(city.getId());
						handler.sendEmptyMessage(3);
					}
					@Override
					public void onFailed(String message) {
						// TODO Auto-generated method stub
						handler.sendEmptyMessage(0);
					}
				});

	}

	class ProvinceAdapter extends BaseAdapter {
		private List<Province> pList;
		private LayoutInflater mInflater;
		private int selectItem = -1;
		public ProvinceAdapter(Context context, List<Province> pList) {
			this.pList = pList;
			mInflater = LayoutInflater.from(context);
		}
		public void setSelectItem(int selectItem) {
			this.selectItem = selectItem;
			tv_province.setText(pList.get(selectItem).getProvinceName());
			notifyDataSetInvalidated();
		}
		public void refresh(List<Province> pList) {
			this.pList = pList;
			notifyDataSetChanged();
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
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.activity_chose_city_item, null);
				holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv_name.setText(pList.get(position).getProvinceName());
			if (position == selectItem) {
				convertView.setBackgroundColor(Color.GREEN);
			} else {
				convertView.setBackgroundColor(Color.GRAY);
			}
			return convertView;
		}
	}

	class CityAdapter extends BaseAdapter {
		private List<City> cList;
		private LayoutInflater mInflater;
		private int selectItem = -1;
		public CityAdapter(Context context, List<City> cList) {
			this.cList = cList;
			mInflater = LayoutInflater.from(context);
		}
		public void setSelectItem(int selectItem) {
			this.selectItem = selectItem;
			tv_city.setText(cList.get(selectItem).getCityName());
			notifyDataSetInvalidated();
		}
		public void refresh(List<City> cList) {
			this.cList = cList;
			notifyDataSetChanged();
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return cList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return cList.get(position);
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
				convertView = mInflater.inflate(R.layout.activity_chose_city_item, null);
				holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv_name.setText(cList.get(position).getCityName());
			if (position == selectItem) {
				convertView.setBackgroundColor(Color.RED);
			} else {
				convertView.setBackgroundColor(Color.WHITE);
			}
			return convertView;
		}
	}

	class CountryAdapter extends BaseAdapter {
		private List<Country> cList;
		private LayoutInflater mInflater;
		private int selectItem = -1;
		public CountryAdapter(Context context, List<Country> cList) {
			this.cList = cList;
			mInflater = LayoutInflater.from(context);
		}
		public void setSelectItem(int selectItem) {
			this.selectItem = selectItem;
			tv_country.setText(cList.get(selectItem).getCountryName());
			notifyDataSetInvalidated();
		}

		public void refresh(List<Country> cList) {
			this.cList = cList;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return cList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return cList.get(position);
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
						R.layout.activity_chose_city_item, null);
				holder.tv_name = (TextView) convertView
						.findViewById(R.id.tv_name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv_name.setText(cList.get(position).getCountryName());
			if (position == selectItem) {
				convertView.setBackgroundColor(Color.CYAN);
			} else {
				convertView.setBackgroundColor(Color.LTGRAY);
			}
			return convertView;
		}
	}

	class ViewHolder {
		TextView tv_name;
	}
}
