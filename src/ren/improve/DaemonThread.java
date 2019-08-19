package ren.improve;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/19 15:03
 * @description：守护线程
 */
public class DaemonThread {
    private static class UseThread extends Thread {
        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + " am extends Thread");
                }
            } finally {
                System.out.println("finally…………………");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        UseThread useThread = new UseThread();
        //设置线程为守护线程
        useThread.setDaemon(true);
        useThread.start();
        Thread.sleep(50);
        //守护线程：当前线程会跟随主线程一并结束，故不interrupt，线程也会停止执行
        //useThread.interrupt();
    }
}
