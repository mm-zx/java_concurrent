package ren.improve.vol;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/20 13:25
 * @description： 验证volatile没有原子性
 */
public class VolatileUnsafe {
    /*volatile只保证内存可见性，不保证操作的原子性
     * 使用场景：某个变量只有一个线程进行修改，其他线程都是读取时（
     * 要求高可见低于原子性，一写多读）使用volatile
     * */
    private static class VolatileVar implements Runnable {
        private static Object object = new Object();
        private volatile int a = 0;

        @Override
        public void run() {
            try {
                synchronized (object) {
                    a++;
                    System.out.println(Thread.currentThread().getName() + ":----- " + a);
                    Thread.sleep(100);
                    a++;
                    System.out.println(Thread.currentThread().getName() + ":----- " + a);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        VolatileVar volatileVar = new VolatileVar();
        Thread t1 = new Thread(volatileVar);
        Thread t2 = new Thread(volatileVar);
        Thread t3 = new Thread(volatileVar);
        Thread t4 = new Thread(volatileVar);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
