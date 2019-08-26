package ren.improve.wn;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/20 14:37
 * @description： 快递追踪程序，当快递公里数变化或者到站通知相应的线程进行业务处理
 */
public class TestWN {
    private static Express express = new Express(0, Express.CITY);

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
