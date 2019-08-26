package ren.improve.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/26 15:14
 * @description： 演示基本类型的原子操作
 */
public class UseAtomicInt {
    static AtomicInteger ai = new AtomicInteger(10);

    public static void main(String[] args) {
        System.out.println(ai.getAndIncrement());//get在前先取值再累加
        System.out.println(ai.get());
        System.out.println(ai.incrementAndGet());//get在后先累加

    }
}

