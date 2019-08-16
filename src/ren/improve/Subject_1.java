package ren.improve;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/16 16:29
 * @description：新启线程的方式
 */
public class Subject_1 {
    /* extends Thread */
    private static class UserThread extends Thread {
        @Override
        public void run() {
            super.run();
            System.out.println(Thread.currentThread().getName() + " am extends Thread");
        }
    }

    /* implements Runnable */
    private static class UserRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " am implements Runnable");
        }
    }

    /* implements Callable */
    private static class UserCallable implements Callable<String> {

        @Override
        public String call() {
            System.out.println(Thread.currentThread().getName() + " am implements Callable");
            return "Callable result";
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        UserThread userThread = new UserThread();
        userThread.start();
        UserRunnable userRunnable = new UserRunnable();

        new Thread(userRunnable).start();
        UserCallable userCallable = new UserCallable();
        FutureTask<String> task = new FutureTask<>(userCallable);//包装 Callable
        new Thread(task).start();
        System.out.println("Get UserCallable Result " + task.get());
    }
}
