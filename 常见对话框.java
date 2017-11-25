package com.example.commondialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d("MainActivity", "onCreate");
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.d("MainActivity", "onStart");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.d("MainActivity", "onResume");
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.d("MainActivity", "onPause");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		Log.d("MainActivity", "onStop");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d("MainActivity", "onDestroy");
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
	}
	
	
	
	public void showNormal(View view){
		/**
		 * 1. 先创建一个Builder对象
		 */
		Builder builder = new AlertDialog.Builder(this);
		/**
		 * 2. 配置各个属性
		 */
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("请做出您的选择");
		//设置提示的消息
		builder.setMessage("您愿意嫁给威风吗？");
		//设置取消和确定按钮
		builder.setNegativeButton("不愿意", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(MainActivity.this, "您做出了明智的选择", Toast.LENGTH_SHORT).show();
				
			}
		});
		builder.setPositiveButton("愿意", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(MainActivity.this, "你做出了错误的选择", Toast.LENGTH_SHORT).show();
			}
		});
		
		/*
		 * 3. 创建出对象
		 */
		AlertDialog alertDialog = builder.create();
		
		//配置点击外侧区域不自动销毁
		alertDialog.setCanceledOnTouchOutside(false);
		alertDialog.setCancelable(false);//点击 back键也不取消
		/*
		 * 4. 显示对话框 
		 */
		alertDialog.show();
		
	}
	
	
	int checkedItem = -1;//默认选中的条目的索引
	public void showSingle(View view){
		final String[] items = {"男","女","保密"};
		AlertDialog alertDialog = new AlertDialog.Builder(this)
		.setIcon(R.drawable.ic_launcher)
		.setTitle("选择性别")
		.setSingleChoiceItems(items, checkedItem, new OnClickListener() {
			//参数2：选择的是哪个条目的索引
			@Override
			public void onClick(DialogInterface dialog, int which) {
				checkedItem = which;
				Toast.makeText(MainActivity.this, items[which], Toast.LENGTH_SHORT).show();
				//销毁当前对话框
				dialog.dismiss();
				
			}
		})
		.create();
		
		alertDialog.show();
//		alertDialog.setOnCancelListener(new OnCancelListener() {
//			
//			@Override
//			public void onCancel(DialogInterface dialog) {
//				
//			}
//		});
//		alertDialog.cancel();//如果对话框设置了取消监听器，那么通过该代码取消对话框的时候，监听器还有执行
//		alertDialog.dismiss();//如果对话框设置了取消监听器，那么通过该代码取消对话框的时候，监听器不会执行
		
		
		
		
	}
	boolean[] checkedItems = {false,false,false,false,false,false,false,false,false,false,false};
	public void showMulti(View view){
		Builder builder = new AlertDialog.Builder(this);
		
		builder.setTitle("请选择您感兴趣的频道");
		
		final String[] items = {"财经","两性","房产","公开课","教育","汽车","美女","帅哥","IT培训","工程","直播"};
		
		builder.setMultiChoiceItems(items, checkedItems, new OnMultiChoiceClickListener() {
			
			/**
			 * 参数 1：alertDialog对象本身
			 * 参数2：点中的条目的脚标
			 * 参数3：本次点击时选中了还是取消选中了
			 */
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				
				checkedItems[which] = isChecked;
				
				Toast.makeText(getApplicationContext(), items[which]+(isChecked?"选中了":"取消选中了"), Toast.LENGTH_SHORT).show();
			}
		});
		
		builder.setPositiveButton("确定", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(getApplicationContext(), "您确定了", Toast.LENGTH_SHORT).show();
			}
		});
		
		AlertDialog alertDialog = builder.create();
		
		alertDialog.show();
	}
	
	public void showHorizontalProgress(View view){
		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setIcon(R.drawable.ic_launcher);
		progressDialog.setTitle("下载中");
		progressDialog.setMessage("请稍后");
		//将进度条变为水平样式
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		
		progressDialog.show();
		
		//模拟缓慢的下载过程，从0更新到100
		/*new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		}).start();*/
		
		new Thread(){
			@Override
			public void run() {
				for(int i=1;i<=100;i++){
					SystemClock.sleep(50);
					progressDialog.setProgress(i);
				}
				//销毁进度条对话框
				progressDialog.dismiss();
//				progressDialog.cancel(); 跟上面同样的效果
				
			};
			
		}.start();
		
		
	}
	
	public void showNormalProgress(View view){
		ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setIcon(R.drawable.ic_launcher);
		progressDialog.setTitle("正在下载");
		progressDialog.setMessage("玩命下载中....");
		progressDialog.show();
		
	}
	
	
	
	
	

}