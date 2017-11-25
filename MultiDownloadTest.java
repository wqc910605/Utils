package com;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.tree.DefaultTreeCellEditor.EditorContainer;

public class MultiDownloadTest {

	public static void main(String[] args) throws MalformedURLException, IOException {
		/**
		 * 1. 获取目标文件的总大小
		 * 
		 * http://localhost:8080/b.jpg
		 */
		int totalSize = new URL("http://localhost:8080/b.jpg").openConnection().getContentLength();
		/**
		 * 2. 确定要开启几个线程
		 */
//		int threadCount = Runtime.getRuntime().availableProcessors();
		int threadCount = 3;
		
		System.out.println("totalSize="+totalSize+"/threadCount="+threadCount);
		
		/**
		 * 3. 计算avgSize
		 */
		int avgSize = totalSize / threadCount;
		
		/**
		 * 4. 计算每个线程要下载的脚标范围
		 */
		for(int i = 1;i<=threadCount;i++){
			int startIndex = (i-1)*avgSize;
			int endIndex = i*avgSize - 1;
			if (i==threadCount) {
				endIndex = totalSize -1;
			}
			new DownloadThread(startIndex, endIndex, i).start();
		}
		
	}
	static class DownloadThread extends Thread{
		private int startIndex;
		private int endIndex;
		private int id;
		
		public DownloadThread(int startIndex, int endIndex, int id) {
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			this.id = id;
		}

		@Override
		public void run() {
			try {
				URL url = new URL("http://localhost:8080/b.jpg");
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				/**
				 * 配置请求参数，仅仅获取文件的部分
				 */
				connection.setRequestProperty("Range", "bytes="+startIndex+"-"+endIndex);
				InputStream is = connection.getInputStream();
				/**
				 * 写文件到特定的位置
				 */
				RandomAccessFile raf = new RandomAccessFile("c:/java/b.jpg", "rw");
				
				/**
				 * 文件的copy
				 */
				/**
				 * 注意：：： 移动raf到特定的位置
				 */
				raf.seek(startIndex);
				
				byte[] buffer = new byte[10];
				int len;
				int total = 0;
				while((len=is.read(buffer))!=-1){
					raf.write(buffer, 0, len);
					total+=len;
					float percent = 100*(total+0.0f) / (endIndex - startIndex +1);
					System.out.println("thread"+id+"下载了"+percent+"%");
				}
				
				raf.close();
				is.close();
				System.out.println("thread"+id+"下载完成了。下载的范围是：["+startIndex+","+endIndex+"]");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
	}

}
