package ren.improve.ThreadL;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/20 13:38
 * @description： 演示ThreadLocal的使用
 */
public class UseThreadLocal {
    //线程安全的map<Thread id, ThreadLocal value>
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {//赋初始值
            return 1;
        }
    };

    //static ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> {//java8赋初始值
    //        return 1;
    //    });
    //

    /**
     * 运行3个线程
     */
    private void starThreadArray() {
        Thread[] run = new Thread[3];
        for (int i = 0; i < run.length; i++) {
            run[i] = new Thread(new TestThread(i));
        }
        for (Thread thread : run) {
            thread.start();
        }
    }

    public static class TestThread implements Runnable {
        private int id;

        TestThread(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ": start");
            Integer s = threadLocal.get();//获得变量的值
            s += id;
            threadLocal.set(s);
            System.out.println(Thread.currentThread().getName() + ": " + threadLocal.get());
        }
    }

    public static void main(String[] args) {
        UseThreadLocal useThreadLocal = new UseThreadLocal();
        useThreadLocal.starThreadArray();
    }
}
