package ren.improve.lock.rw;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/23 17:19
 * @description： 对商品进行业务的应用
 */
public class BusiApp {
    private static final int readWriteRatio = 10;//读写线程的比例
    private static final int minThreadCount = 3;//最少线程数
    private static CountDownLatch latch = new CountDownLatch(1);

    private static class GetThread implements Runnable {
        private GoodsService goodsService;

        GetThread(GoodsService goodsService) {
            this.goodsService = goodsService;
        }

        @Override
        public void run() {
            try {
                latch.await();//让读写线程同时运行
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long start = System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {
                goodsService.getNum();
            }
            System.out.println(Thread.currentThread().getName() + "读取商品数据耗时"
                    + (System.currentTimeMillis() - start) + " ms");
        }
    }

    private static class SetThread implements Runnable {
        private GoodsService goodsService;

        SetThread(GoodsService goodsService) {
            this.goodsService = goodsService;
        }

        @Override
        public void run() {
            try {
                latch.await();//让读写线程同时运行
                long start = System.currentTimeMillis();
                Random random = new Random();
                for (int i = 0; i < 10; i++) {//操作10次
                    Thread.sleep(50);
                    goodsService.setNum(random.nextInt(10));
                }
                System.out.println(Thread.currentThread().getName() + "写商品数据耗时"
                        + (System.currentTimeMillis() - start) + " ms");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        GoodsInfo goodsInfo = new GoodsInfo("Cup", 100000, 10000);
//        GoodsService goodsService = new UseSyn(goodsInfo);//使用synchronized
        GoodsService goodsService = new UseRwLock(goodsInfo);
        for (int i = 0; i < minThreadCount; i++) {
            Thread setThread = new Thread(new SetThread(goodsService));
            for (int j = 0; j < readWriteRatio; j++) {
                Thread getThread = new Thread(new GetThread(goodsService));
                getThread.start();
            }
            setThread.start();
        }
        latch.countDown();
    }
}
