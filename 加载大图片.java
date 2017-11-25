package com.example.loadbigimage;

import java.io.File;

import android.R.integer;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageView = (ImageView) findViewById(R.id.iv);
		
//		int width2 = imageView.getWidth();
//		int height2 = imageView.getHeight();
//		Log.d("MainActivity", "onCreate:width2="+width2+"/height2="+height2);
		
		
	}
	
	public void load(View view){
		//先经xxx.jpg/.png 转换为 Android中的图片对象Bitmap
		//将Bitmap显示到ImageView控件上
		File file = new File(Environment.getExternalStorageDirectory(),"me.jpg");
//		Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//		imageView.setImageBitmap(bitmap);
		
		//1. 获取图片的宽 width 和图片的高 height。
		Options opts  = new Options();
		opts.inJustDecodeBounds = true;//仅仅获取图片的边界信息
		//如果配置了上面的参数，并且为true，那么该方法返回的null
		BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
		int width = opts.outWidth;
		int height = opts.outHeight;
		Log.d("MainActivity", "width="+width+"/height="+height);
		//2. 获取ImageView的 width2，和高height2
		int width2 = imageView.getWidth();
		int height2 = imageView.getHeight();
		Log.d("MainActivity", "width2="+width2+"/height2="+height2);
		//3.  计算等比例缩放的倍数 sampleSize  = Math.max(witth/width,height/height2);
		int sampleSize = Math.max(width/width2, height/height2);
		//4. 使用BitmapFactory缩放模式加载图片  ---> Bitmap
		opts.inJustDecodeBounds = false;//不是仅仅获取图片的边界，而是要加载图片
		opts.inSampleSize= sampleSize;//告诉工厂，以等比例缩放的模式加载图片
		Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
		//5. 显示到界面
		imageView.setImageBitmap(bitmap);
		
	}

}


