package ren.improve.syn;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/19 17:48
 * @description：验证多线程下的变量共享 增加synchronized 可以保证我们程序有正确的行为
 */
public class SynTest {
    //共享变量
    private int age = 100000;//共享变量

    //递增方法
    private synchronized void add() {//实例方法  锁定是对象的实例  对象锁
        age++;
    }

    //递增方法
    private synchronized void remove() {
        age--;
    }

    private synchronized int getAge() {
        return age;
    }

    private synchronized static void adda() {//静态方法（类方法）  类锁

    }

    //递增age
    private static class TestThread extends Thread {
        private SynTest synTest;

        TestThread(SynTest synTest, String name) {
            super(name);
            this.synTest = synTest;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100000; i++) {//递增100000
                synTest.add();
            }
            System.out.println(Thread.currentThread().getName() + " age = " + synTest.getAge());
        }
    }

    public static void main(String[] args) {
        SynTest synTest = new SynTest();
        TestThread testThread = new TestThread(synTest, "TestThread");
        testThread.start();
        for (int i = 0; i < 100000; i++) {
            synTest.remove();
        }
        System.out.println(Thread.currentThread().getName() + " age = " + synTest.getAge());
    }
}
