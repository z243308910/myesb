package com.echounion.boss.test;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceText {

	public static void main(String[] args) throws IOException, InterruptedException {
        // 创建一个固定大小的线程池
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i <10; i++) {
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    for(int j=0;j<10;j++)
                    {
                    	System.out.println(Thread.currentThread()+">>>>>>>>>>>>>>>>>>>>"+j);
                    	try
                    	{
                    		Thread.sleep(500);
                    	}catch (Exception e) {
						}
                    }
                }
            };
            // 在未来某个时间执行给定的命令
//            service.execute(run);
//            Callable<?> call=Executors.callable(run);
            Future<?> f= service.submit(run);
            try {
				System.out.println(Thread.currentThread()+"得到线程返回"+f.get());
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
        }
        // 关闭启动线程
        service.shutdown();
        // 等待子线程结束，再继续执行下面的代码
        service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        System.out.println("all thread complete");
    }
}
