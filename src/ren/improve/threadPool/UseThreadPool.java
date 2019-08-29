package ren.improve.threadPool;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/29 15:41
 * @description： 线程池的使用范例
 */
public class UseThreadPool {
    static class Worker implements Runnable {
        private String taskName;
        private Random random = new Random();

        public Worker(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskName() {
            return taskName;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " process the task : " + taskName);
            try {
                Thread.sleep(random.nextInt(100) * 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public static void main(String[] args) {
            // ExecutorService threadPool = new ThreadPoolExecutor(2, 4, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<>(3), new ThreadPoolExecutor.DiscardOldestPolicy());
            //ExecutorService threadPool = Executors.newFixedThreadPool(2);
//            ExecutorService threadPool = Executors.newCachedThreadPool();
//            ExecutorService threadPool = Executors.newSingleThreadExecutor();
            ExecutorService threadPool = Executors.newWorkStealingPool();
            for (int i = 0; i < 7; i++) {
                Worker worker = new Worker("Worker" + i);
                System.out.println("A new task has been added: " + worker.getTaskName());
                threadPool.execute(worker);
            }
            threadPool.shutdown();
        }
    }
}
