package com.example.sendBroadCast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void sendNormal(View view){
		Intent intent = new Intent();
		intent.setAction("com.itheima.myapp.send");
		intent.putExtra("data", "我是发送的时候携带的数据");
		
		sendBroadcast(intent);
	}
	
	public void sendOrdered(View view){
		Intent intent = new Intent();
		intent.setAction("gov.center.china");
		/**
		 * 参数2：接收者应该具备的权限，null就是不需要
		 * 参数3：最终广播接收者，如果有其他广播接收者，其他广播接收者先收广播，最后该广播接收者才会被调用
		 * 参数4：Handler，scheduler,用于决定，参数3中的onReceive方法在哪个线程中被调用
		 * 参数5/6/7：绑定到广播上的初始化的数据
		 */
		sendOrderedBroadcast(intent, null,new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				Log.d("MainActivity", "我是resultReceiver，被调用了，ThreadName="+Thread.currentThread().getName());
				String resultData = getResultData();
				Log.d("MainActivity", "到最后钱是："+resultData);
				
			}
		}, null, 0, "每人1w元", null);
	}
	
	

}
