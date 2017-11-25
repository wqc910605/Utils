package com.example.bitmapduang;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageView = (ImageView) findViewById(R.id.iv);
	}
	
	public void scale(View view){
		//1. 加载模特
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
		//2. 创建画板和画纸（空白的Bitmap）
		Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		//创建画板的同时把空白的画纸贴上去
		Canvas canvas = new Canvas(newBitmap);
		Matrix matrix = new Matrix();
		float[] values = {  1,0,0,
							0,1,0,
							0,0,0.5f};
		
		matrix.setValues(values);
		
		//3. 调用Canvas的画画功能
		//参数1：要绘制的模特
		//参数2：矩阵，用于给Bitmap进行特效处理
		canvas.drawBitmap(bitmap, matrix, null);
//		
//		{x,y,z} * {
//			       2,0,0,  = {2x,2y,z};
//			       0,2,0,
//			       0,0,1
//				}
		
		//显示到界面
		imageView.setImageBitmap(newBitmap);
		
	}
	
	public void translate(View view){
		//1. 加载模特
				Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
				//2. 创建画板和画纸（空白的Bitmap）
				Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
				//创建画板的同时把空白的画纸贴上去
				Canvas canvas = new Canvas(newBitmap);
				Matrix matrix = new Matrix();
				matrix.setTranslate(10, -10);
				
				//3. 调用Canvas的画画功能
				//参数1：要绘制的模特
				//参数2：矩阵，用于给Bitmap进行特效处理
				canvas.drawBitmap(bitmap, matrix, null);
//				
//				{x,y,z} * {
//					       2,0,0,  = {x+10,y,z};
//					       0,2,0,
//					       0,0,1
//						}
				
				//显示到界面
				imageView.setImageBitmap(newBitmap);
	}
	
	public void reverse(View view){
				//1. 加载模特
				Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
				//2. 创建画板和画纸（空白的Bitmap）
				Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
				//创建画板的同时把空白的画纸贴上去
				Canvas canvas = new Canvas(newBitmap);
				Matrix matrix = new Matrix();
				float[] values = {  1,0,0,
									0,-1,0,
									0,0,1};
				
				matrix.setValues(values);
//				matrix.setTranslate(0, newBitmap.getHeight()/2);//单纯的赋值，会覆盖矩阵老的数值
				matrix.postTranslate(0, newBitmap.getHeight());//在matrix老的值基础上叠加效果
				
				//3. 调用Canvas的画画功能
				//参数1：要绘制的模特
				//参数2：矩阵，用于给Bitmap进行特效处理
				canvas.drawBitmap(bitmap, matrix, null);
//				
//				{x,y,z} * {
//					       1,0,0,  = {x,-y,z};
//					       0,-1,0,
//					       0,0,1
//						}
				
				//显示到界面
				imageView.setImageBitmap(newBitmap);
	}
	
	public void rotate(View view){
		//1. 加载模特
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
		//2. 创建画板和画纸（空白的Bitmap）
		Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		//创建画板的同时把空白的画纸贴上去
		Canvas canvas = new Canvas(newBitmap);
		Matrix matrix = new Matrix();
//		matrix.setRotate(20);//绕着左上角点旋转
		matrix.setRotate(180, newBitmap.getWidth()/2, newBitmap.getHeight()/2);//绕着中心点
		
		//3. 调用Canvas的画画功能
		//参数1：要绘制的模特
		//参数2：矩阵，用于给Bitmap进行特效处理
		canvas.drawBitmap(bitmap, matrix, null);
//		
//		{x,y,z} * {
//			       2,0,0,  = {x+10,y,z};
//			       0,2,0,
//			       0,0,1
//				}
		
		//显示到界面
		imageView.setImageBitmap(newBitmap);
	}
	
	
}

