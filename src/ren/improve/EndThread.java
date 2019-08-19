package ren.improve;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/19 10:52
 * @description：中断扩展自Thread类的线程
 */
public class EndThread {
    private static class UseThread extends Thread {
        UseThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (!isInterrupted()){
                System.out.println(Thread.currentThread().getName() + " am extends Thread");
            }
            System.out.println(Thread.currentThread().getName() + " interrupt flag is " + isInterrupted());

        }
    }

    public static void main(String[] args) throws InterruptedException {
        UseThread useThread = new UseThread("EndThread");
        useThread.start();
        Thread.sleep(20);
        //20毫秒后执行后续操作（20毫秒后给useThread打上中断标志位）
        useThread.interrupt();
    }
}
