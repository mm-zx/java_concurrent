package ren.improve.delayOrderDemo;

import java.util.concurrent.DelayQueue;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/30 17:03
 * @description： 延时队列测试程序
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        DelayQueue<ItemVo<Order>> queue = new DelayQueue<>();//初始化延时队列
        new Thread(new PutOrder(queue)).start();
        new Thread(new FetchOrder(queue)).start();

        // 每隔500毫秒打印数字
        for (int i=1;i<20;i++){
            Thread.sleep(500);
            System.out.println(i * 500);
        }
    }
}
