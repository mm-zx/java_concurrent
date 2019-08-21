package ren.improve.tool.semaphore;

import java.sql.Connection;
import java.util.Random;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/21 15:25
 * @description： 测试数据库连接池
 */
public class AppTest {
    private static DBPoolSemaphore dbPool = new DBPoolSemaphore();

    //拿数据库连接的线程
    private static class BusiThread extends Thread {
        @Override
        public void run() {
            Random r = new Random();//让每个线程持有的时间不一样
            long start = System.currentTimeMillis();
            try {
                Connection connect = dbPool.takeConnect();
                System.out.println("Thread_" + Thread.currentThread().getId() + "_获取数据库连接共耗时[" + (System.currentTimeMillis() - start) + "] ms");
                Thread.sleep(100 + r.nextInt(100));//模拟业务操作，线程持有连接查询数据
                System.out.println("查询数据完成，归还连接！");
                dbPool.returnConnect(connect);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            Thread thread = new BusiThread();
            thread.start();
        }
    }
}
