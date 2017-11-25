package com.example.receiveMyBroadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String stringExtra = intent.getStringExtra("data");
		Log.d("MyReceiver", "接收到广播了："+stringExtra);
	}

}
