package ren.improve.syn;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/20 12:30
 * @description： 验证类锁对象锁的关联
 *                  验证同一类不同实例之间锁的关联,实例锁之间没有关联
 */
public class SynClassAndInstance {
    //使用类锁的线程
    private static class SynClass extends Thread {
        @Override
        public void run() {
            System.out.println("TestClass is running ………………");
            synClass();
        }
    }

    //类锁
    private synchronized static void synClass() {
        try {
            Thread.sleep(1000);
            System.out.println("synClass is going");
            Thread.sleep(1000);
            System.out.println("synClass is end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //使用实例锁的线程
    private static class InstanceSyn implements Runnable {
        private SynClassAndInstance synClassAndInstance;

        public InstanceSyn(SynClassAndInstance synClassAndInstance) {
            this.synClassAndInstance = synClassAndInstance;
        }

        @Override
        public void run() {
            System.out.println("TestInstance is running " + synClassAndInstance);
            synClassAndInstance.instance();
        }
    }

    //使用实例锁的线程
    private static class Instance2Syn implements Runnable {
        private SynClassAndInstance synClassAndInstance;

        public Instance2Syn(SynClassAndInstance synClassAndInstance) {
            this.synClassAndInstance = synClassAndInstance;
        }

        @Override
        public void run() {
            System.out.println("TestInstance2 is running " + synClassAndInstance);
            synClassAndInstance.instance2();
        }
    }

    //实例锁
    private synchronized void instance() {
        try {
            Thread.sleep(3000);
            System.out.println("synInstance is going " + this.toString());
            Thread.sleep(3000);
            System.out.println("synInstance id ended " + this.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void instance2() {
        try {
            Thread.sleep(3000);
            System.out.println("synInstance2 is going " + this.toString());
            Thread.sleep(3000);
            System.out.println("synInstance2 id ended " + this.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SynClassAndInstance synClassAndInstance = new SynClassAndInstance();
        Thread t1 = new SynClass();//类锁
        Thread t2 = new Thread(new InstanceSyn(synClassAndInstance));//实例锁线程
        Thread t3 = new Thread(new Instance2Syn(synClassAndInstance));//实例锁线程
        SynClassAndInstance synClassAndInstance1 = new SynClassAndInstance();//新实例
        Thread t4 = new Thread((new InstanceSyn(synClassAndInstance1)));//锁一个新实例
        t4.start();
        t2.start();
        t3.start();
        Thread.sleep(1000);
//        t1.start();
    }
}
