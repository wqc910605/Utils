package com.itheima.mobilesafe22.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;

public class ServiceStateUtil {
	
	//判断传递进来的服务是否是运行状态如果是则返回true;
	
	public static boolean isServiceRunning(Context context,Class<?extends Service> clazz){
		//ActivityManager就是安卓系统的任务管理
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		//假设当前系统有1000个服务正在运行，将前100个服务直接返回，假设当前系统有20个服务正在运行，直接将20个服务全部返回
		List<RunningServiceInfo> runningServices = activityManager.getRunningServices(100);
		//遍历所有正在执行的服务，如果与传递进来的服务一致，说明传递进来的服务正在运行
		for (RunningServiceInfo runningServiceInfo : runningServices) {
			ComponentName componentName = runningServiceInfo.service;
			if (TextUtils.equals(componentName.getClassName(), clazz.getName())) {
				return true;
			}
		}
		
		return false;
	}


}
