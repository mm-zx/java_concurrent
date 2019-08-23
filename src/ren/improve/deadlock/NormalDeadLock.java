package ren.improve.deadlock;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/23 11:44
 * @description： 普通的死锁情况 当前情况解决方法：统一先获取第一个锁再获取第二个锁
 */
public class NormalDeadLock {
    private static final Object valueFirst = new Object();//第一个锁
    private static final Object valueSecond = new Object();//第二个锁

    private static void firstToSecond() {
        String threadName = Thread.currentThread().getName();
        synchronized (valueFirst) {
            System.out.println(threadName + "get first");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (valueSecond) {
                System.out.println(threadName + "get second");
            }
        }
    }

    private static void secondToFirst() {
        String threadName = Thread.currentThread().getName();
        synchronized (valueSecond) {
            System.out.println(threadName + "get first");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (valueFirst) {
                System.out.println(threadName + "get second");
            }
        }
    }

    //先拿第二个锁
    private static class TestThread extends Thread {
        public TestThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            secondToFirst();
        }
    }

    public static void main(String[] args) {
        Thread.currentThread().setName("TestDeadLock");
        TestThread testThread = new TestThread("SubTestThread");
        testThread.start();
        firstToSecond();
    }
}

