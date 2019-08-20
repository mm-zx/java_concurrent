package ren.improve.join;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/20 14:53
 * @description： 演示join使用
 */
public class UseJoin {
    static class JumpQueue implements Runnable {
        private Thread thread;

        public JumpQueue(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ": terminate .");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //previous是主线程
        Thread previous = Thread.currentThread();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new JumpQueue(previous), String.valueOf(i));
            System.out.println(previous.getName() + " insert into " + thread.getName());
            thread.start();
            previous = thread;
        }
        Thread.sleep(2000);
        System.out.println(Thread.currentThread().getName() + ": terminate .");
    }
}
