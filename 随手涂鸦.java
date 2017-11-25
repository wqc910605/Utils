package com.example.tuya;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ImageView imageView;
	private Bitmap bitmap;
	private Canvas canvas;
	private Paint paint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageView = (ImageView) findViewById(R.id.iv);
		//1. 给控件绑定触摸监听器
		imageView.setOnTouchListener(new OnTouchListener() {
			
			private float startX;
			private float startY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.d("MainActivity", "event="+event.getAction());
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY = event.getY();
					if (bitmap==null) {
						//初始化画板、画笔和画纸
						int width = imageView.getWidth();
						int height = imageView.getHeight();
						bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
						canvas = new Canvas(bitmap);
						//将bitmap绘制成白色的
						canvas.drawColor(Color.WHITE);
						paint = new Paint();
						//设置笔的颜色和粗细
						paint.setColor(Color.RED);
						paint.setStrokeWidth(4);//像素
					}
					
					break;
				case MotionEvent.ACTION_MOVE:
					//先获取当前move到的坐标
					float currentX = event.getX();
					float currentY = event.getY();
					//结合起始点，绘制直线
					
					canvas.drawLine(startX, startY, currentX, currentY, paint);
					//下一条的线段的起点就是上一条线段的终点
					startX = currentX;
					startY = currentY;
					
					imageView.setImageBitmap(bitmap);
					
					break;
				default:
					break;
				}
				
				return true;
			}
		});
	}
	
	public void save(View view) throws IOException{
		File file = new File(Environment.getExternalStorageDirectory(), "IMAGE_"+System.currentTimeMillis()+".jpg");
		FileOutputStream fos = new FileOutputStream(file);
		//将Bitmap保存为jpg格式
		/**
		 * 参数1：图片格式
		 * 参数2：图片压缩质量
		 * 参数3：输出流，用于输出图片
		 */
		bitmap.compress(CompressFormat.JPEG, 100, fos);
		fos.close();
		Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
		//给图库发送广播，让图库立即将我们的图片添加到图库列表中
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		intent.setData(Uri.fromFile(file));//传入文件的路径
		//发送广播
		sendBroadcast(intent);
		
	}
	
	public void clear(View view){
		bitmap = null;
		imageView.setImageBitmap(null);
	}
	
	
}

