package ren.improve.delayOrderDemo;

import java.util.concurrent.DelayQueue;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/30 16:58
 * @description： 将订单推入队列
 */
public class PutOrder implements Runnable {
    private DelayQueue<ItemVo<Order>> queue;

    public PutOrder(DelayQueue<ItemVo<Order>> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        //5秒到期
        Order orderTb = new Order("Tb12345", 366);
        ItemVo<Order> itemTb = new ItemVo<>(5000, orderTb);
        queue.offer(itemTb);
        System.out.println("订单5秒后超时：data=" + orderTb.getORDER_NO() + "; " + orderTb.getORDER_MONEY());
        //8秒后到期
        Order orderJd = new Order("Jd54321", 366);
        ItemVo<Order> itemJd = new ItemVo<>(8000, orderJd);
        queue.offer(itemJd);
        System.out.println("订单5秒后超时：data=" + orderJd.getORDER_NO() + "; " + orderJd.getORDER_MONEY());


    }
}
