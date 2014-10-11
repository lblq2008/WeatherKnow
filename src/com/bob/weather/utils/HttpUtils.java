package com.bob.weather.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.bob.weather.inf.HttpCallBackListener;

import android.app.ProgressDialog;

/**
 * HTTP 请求工具类
 * 
 * @ClassName HttpUtils
 * @author Bob
 * @date 2014-10-11 下午02:41:16
 */
public class HttpUtils {

	public static final int TIMEOUT = 10 * 1000;

	/**
	 * HTTP请求方法
	 * @param progressDialog 圆形加载控件
	 * @param path 请求地址
	 * @param param 请求参数
	 * @param listener 回调
	 * @date 2014-10-11 下午02:43:12
	 */
	public static void requestHttpPost(ProgressDialog progressDialog,
			final String path, final String param,
			final HttpCallBackListener listener) {
		if (progressDialog != null) {
			progressDialog.show();
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpURLConnection urlConnection = null ;
				try {
					String methed = "POST" ;
					URL url = new URL(path);
					urlConnection = (HttpURLConnection) url.openConnection();
					urlConnection.setConnectTimeout(TIMEOUT);
					urlConnection.setReadTimeout(TIMEOUT);
					urlConnection.setDoInput(true);
					urlConnection.setDoOutput(true);
					urlConnection.setRequestMethod(methed);
					
					if(methed.equals("POST")){
						DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
						dataOutputStream.writeBytes("paramKey" + "=" + param);
					}
					int code = urlConnection.getResponseCode() ;
					
					InputStream inputStream = urlConnection.getInputStream();
					String response = inputStreamToString(inputStream);
					if(code == 200){
						if(listener != null){
							listener.onSuccess(response);
						}
					}else{
						if(listener != null){
							listener.onFailed(response);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					if(listener != null){
						listener.onFailed("");
					}
				}finally{
					if(urlConnection != null){
						urlConnection.disconnect();
					}
				}
			}
		}).start();
	}
	
	/**
	 * 将InputStream转化成String字符串
	 * @param in
	 * @return
	 * @date 2014-10-11 下午03:14:11
	 */
	public static String inputStreamToString(InputStream in){
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder response = new StringBuilder();
		String line ;
		try {
			while((line = reader.readLine())!=null){
				response.append(line);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return response.toString();
	}
}
