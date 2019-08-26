package ren.improve.lock.condition;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/26 11:10
 * @description： 用lock和condition实现等待和通知机制
 */
public class TestCond {
    private static ExpressCond express = new ExpressCond(0, ExpressCond.CITY);

    //检查里程数是否变化的线程
    private static class CheckKm extends Thread {

        @Override
        public void run() {
            express.waitKm();
        }
    }

    //检查地点是否变化的线程
    private static class CheckSite extends Thread {

        @Override
        public void run() {
            express.waitSite();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            new CheckKm().start();
            new CheckSite().start();
        }
        Thread.sleep(1000);
        express.changeKm();
        Thread.sleep(1000);
        express.changeSite();
    }
}
