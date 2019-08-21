package ren.improve.tool;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/21 14:22
 * @description： CyclicBarrier的代码演示
 */
public class UseCyclicBarrier {
    private static CyclicBarrier barrier = new CyclicBarrier(5, new CollectThread());
    private static ConcurrentHashMap<String, Long> resultMap = new ConcurrentHashMap<>();//存放子线程工作结果的容器

    public static void main(String[] args) {
        for (int i = 0; i <= 4; i++) {
            Thread thread = new Thread(new SubThread());
            thread.start();
        }
    }

    //进行汇总的线程
    private static class CollectThread implements Runnable {
        @Override
        public void run() {
            StringBuilder result = new StringBuilder();
            for (Map.Entry<String, Long> workResult : resultMap.entrySet()) {
                result.append("[ ").append(workResult.getValue()).append(" ]");
            }
            System.out.println("result = " + result);
            System.out.println("do other business ..........");
        }
    }

    //工作线程
    private static class SubThread implements Runnable {
        @Override
        public void run() {
            long id = Thread.currentThread().getId();
            resultMap.put(id + "", id);
            Random random = new Random();
            if (random.nextBoolean()) {
                try {
                    Thread.sleep(1000 + id);
                    System.out.println("Thread[ " + id + " ] .... do sth");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                barrier.await();
                Thread.sleep(1000 + id);
                System.out.println("Thread[ " + id + " ] .... do my business");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}

