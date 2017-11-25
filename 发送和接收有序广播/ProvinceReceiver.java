package com.example.receiveMyBroadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ProvinceReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String resultData = getResultData();
		Log.d("ProvinceReceiver", "我是省政府：收到："+resultData);
//		setResultData("每人1.5w元");
		abortBroadcast();
		
	}

}
