package ren.improve.delayOrderDemo;

import java.util.concurrent.DelayQueue;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/30 16:55
 * @description： 取出到期订单的功能
 */
public class FetchOrder implements Runnable {
    private DelayQueue<ItemVo<Order>> queue;

    public FetchOrder(DelayQueue<ItemVo<Order>> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ItemVo<Order> item = queue.take();
                Order order = item.getData();
                System.out.println("Get From Queue: data=" + order.getORDER_NO() + ";" + order.getORDER_MONEY());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
