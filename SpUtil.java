package com.itheima.mobilesafe22.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtil {
	
	private static final String CONFIG = "config";
	private static SharedPreferences sp;

	//记录boolean
	public static void saveBoolean(Context context, boolean value, String key){
		if (sp == null) {
			sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		}
		sp.edit().putBoolean(key, value).commit();
	}
	
	//获取boolean
	public static boolean getBoolean(Context context, String key, boolean defValue){
		if (sp == null) {
			sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		}
		return sp.getBoolean(key, defValue);
	}
	
	
	
	//记录int
	public static void saveInt(Context context, int value, String key){
		if (sp == null) {
			sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		}
		sp.edit().putInt(key, value).commit();
	}
	
	//获取int
	public static int getInt(Context context, String key, int defValue){
		if (sp == null) {
			sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		}
		return sp.getInt(key, defValue);
	}

}
