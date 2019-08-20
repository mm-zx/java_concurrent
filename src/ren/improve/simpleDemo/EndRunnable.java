package ren.improve.simpleDemo;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/19 11:01
 * @description： 中断实现Runnable的线程
 */
public class EndRunnable {
    private static class UseRunnable implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread().getName() + " am implements Runnable");
            }
            System.out.println(Thread.currentThread().getName() + " interrupt flag is " + Thread.currentThread().isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        UseRunnable useRunnable = new UseRunnable();
        Thread endThread = new Thread(useRunnable);
        endThread.start();
        Thread.sleep(20);
        endThread.interrupt();
    }
}

