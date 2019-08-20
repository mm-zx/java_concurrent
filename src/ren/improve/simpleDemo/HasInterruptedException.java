package ren.improve.simpleDemo;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/19 11:09
 * @description： run方法中抛出了InterruptedException异常的表现情况
 */
public class HasInterruptedException {
    private static class UseThread extends Thread {
        UseThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    /* 当线程已经执行interrupt后再执行sleep会抛出InterruptedException
                     并且将interrupted置为false，导致线程无法正确中断，
                     我们需要在抛出异常后手动interrupt。*/
                    System.out.println(Thread.currentThread().getName() + " interrupt flag is " + isInterrupted());
                    interrupt();
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " am extends Thread");
            }
            System.out.println(Thread.currentThread().getName() + " interrupt flag is " + isInterrupted());

        }
    }

    public static void main(String[] args) throws InterruptedException {
        UseThread useThread = new UseThread("EndThread");
        useThread.start();
        Thread.sleep(500);
        useThread.interrupt();
    }
}
