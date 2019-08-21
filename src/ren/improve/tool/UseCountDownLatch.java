package ren.improve.tool;

import java.util.concurrent.CountDownLatch;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/21 13:36
 * @description： 使用CountDownLatch工具类
 */
public class UseCountDownLatch {
    /*
     * 5个初始化线程，6个初始化操作，
     * 待操作完成后，主线程业务线程才能继续自己的工作*/
    static CountDownLatch latch = new CountDownLatch(6);

    private static class InitThread implements Runnable {

        @Override
        public void run() {
            System.out.println("Thread " + Thread.currentThread().getName() + " ready init work…………");
            latch.countDown();//减一
            System.out.println("Thread " + Thread.currentThread().getName() + " do other work…………");
        }
    }

    private static class BusiThread implements Runnable {

        @Override
        public void run() {
            try {
                latch.await();//等待其他线程完成工作
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread " + Thread.currentThread().getName() + " do business---------");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //初始化线程负责两个初始化操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                    System.out.println("Thread " + Thread.currentThread().getName() + " ready init work step 1…………");
                    latch.countDown();//减一
                    Thread.sleep(50);
                    System.out.println("Thread " + Thread.currentThread().getName() + " ready init work step 2…………");
                    latch.countDown();//减一
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new BusiThread()).start();
        for (int i = 0; i <= 3; i++) {
            Thread thread = new Thread(new InitThread());
            thread.start();
        }
        latch.await();//等待初始化完成
        System.out.println("Main do other work…………");
    }
}
