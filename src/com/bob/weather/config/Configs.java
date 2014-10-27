package com.bob.weather.config;

/**
 * 放置一些配置信息和静态变量
 * @author Bob
 * @date 2014-10-12 下午3:56:23
 *
 */
public class Configs {
	/**
	 * 应用功能：
	 * 1.可以罗列出全国的省、市、县
	 * 2.可以查看全国的任意省市的天气
	 * 3.可以自由切换城市，并查看其它城市的天气
	 * 4.提供手动更新和后台自动更新天气信息的功能 
	 */
	
	//GitHub地址
	//https://github.com/lblq2008/WeatherKnow.git
	
	public static String BaseUrl = "http://www.weather.com.cn/" ;
	 
	/**
	 * 获取全国所有的省份
	 * 返回：01|北京,02|上海,03|天津,04|重庆,05|黑龙江,06|吉林,07|辽宁,08|内蒙古,09|河北,10|山西,11|陕西,12|山东,13|新疆,14|西藏,15|青海,16|甘肃,17|宁夏,18|河南,19|江苏,20|湖北,21|浙江,22|安徽,23|福建,24|江西,25|湖南,26|贵州,27|四川,28|广东,29|云南,30|广西,31|海南,32|香港,33|澳门,34|台湾
	 */
	public static String ProvincesUrl = BaseUrl + "data/list3/city";
	public static String pxiel = ".xml" ;
	
	/**
	 * 根据省份代号获取所属市：例如代号19；
	 * 返回：1901|南京,1902|无锡,1903|镇江,1904|苏州,1905|南通,1906|扬州,1907|盐城,1908|徐州,1909|淮安,1910|连云港,1911|常州,1912|泰州,1913|宿迁
	 */
	public static String CityUrl = BaseUrl + "data/list3/city19.xml";
	
	/**
	 * 根据市代号获取县：例如代号1904；
	 * 返回：190401|苏州,190402|常熟,190403|张家港,190404|昆山,190405|吴县东山,190406|吴县,190407|吴江,190408|太仓
	 */
	public static String DownTownUrl = BaseUrl + "data/list3/city1904.xml";
	
	/**
	 * 获取地区所对应的天气代号；
	 * 返回：190404|101190404
	 */
	public static String WeatherCodeUrl = BaseUrl + "data/list3/city190404.xml";
	
	/**
	 * 根据天气代号获取天气信息；
	 * 返回：{"weatherinfo":{"city":"昆山","cityid":"101190404","temp1":"25℃","temp2":"17℃","weather":"多云","img1":"d1.gif","img2":"n1.gif","ptime":"11:00"}}
	 */
	public static String WeatherInfoUrl = BaseUrl + "data/cityinfo/";
	public static String html = ".html" ;
	
	public static String WeatherDetailUrl = "http://m.weather.com.cn/data/101190404.html";//暂停维护,不使用
	
	
	public static int currentWeather = 0 ;
}
