package com.example.receiveMyBroadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class CityReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String resultData = getResultData();
		Log.d("CityReceiver", "我是市政府：收到："+resultData);
		setResultData("每人2w元");
	}

}
