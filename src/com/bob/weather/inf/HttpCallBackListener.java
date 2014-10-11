package com.bob.weather.inf;

/**
 * HTTP请求回调
 * @ClassName HttpCallBackListener
 * @author Bob
 * @date 2014-10-11 下午02:38:03 
 */
public interface HttpCallBackListener {
	/**
	 * 请求成功执行的方法
	 * @param response
	 * @date 2014-10-11 下午02:38:52
	 */
	void onSuccess(String response);
	
	/**
	 * 请求失败执行的方法
	 * @param message
	 * @date 2014-10-11 下午02:39:00
	 */
	void onFailed(String message);
}
