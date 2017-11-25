package com;

import java.util.concurrent.Semaphore;

public class ThreadPoolTest {
	
	private static Semaphore semaphore = new Semaphore(3);
	
	public static void main(String[] args) {
		
		for(int i=0;i<20;i++){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					method();

				}
			}).start();
		}
		
	}
	
	private  static void method(){
		try {
			semaphore.acquire();//获取一个信号
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+"进来了");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		System.out.println(Thread.currentThread().getName()+"出去了");
		
		semaphore.release();//释放一个信号
		
	}
	
}
