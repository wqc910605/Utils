package com;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolTest2 {
	private static Executor executor = Executors.newSingleThreadExecutor();
	private static Executor executor2 = Executors.newFixedThreadPool(3);
	private static Executor executor3 = Executors.newCachedThreadPool();
	private static  Executor executor4 = Executors.newScheduledThreadPool(3);//
	private static int corePoolSize = 0;
	private static int maximumPoolSize = 20;
	private static BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(50);
	private static ThreadFactory threadFactory =new ThreadFactory() {
		private AtomicInteger atomicInteger = new AtomicInteger(1);
		@Override
		public Thread newThread(Runnable r) {
			Thread thread = new Thread(r);
			thread.setName("MyThreadpool-"+atomicInteger.getAndIncrement());
			return thread;
		}
	};
	
	/**
	 * 参数1：corePoolSize，核心池大小
	 * 参数2：maximumPoolSize，线程池最多能拥有多少个线程
	 * 参数3：keepAliveTime，当任务执行完后，保存多余（当前的线程个数-corePoolSize）的线程，存活的时间
	 * 参数4：unit，时间单位
	 * 参数5：workQueue，用于存储任务的队列
	 * 参数6：用于创建线程工厂，用于创建线程
	 */
	private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize , 5, TimeUnit.SECONDS, workQueue, threadFactory); 
	
	
	public static void main(String[] args) {
		for(int i=0;i<71;i++){
			threadPoolExecutor.execute(new Runnable() {
				
				@Override
				public void run() {
					method();
					
				}
			});
		}
	}

	private static void method() {
	System.out.println(Thread.currentThread().getName()+"进来了");
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(Thread.currentThread().getName()+"出去了");
		
	}
}
