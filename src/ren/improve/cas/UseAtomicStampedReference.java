package ren.improve.cas;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/26 15:50
 * @description： 带版本戳的原子操作类
 */
public class UseAtomicStampedReference {
    static AtomicStampedReference<String> asr = new AtomicStampedReference<>("zhou", 0);//param2 初始化版本戳

    public static void main(String[] args) throws InterruptedException {
        int oldstamp = asr.getStamp();//拿到初始版本戳
        String oldReference = asr.getReference();
        System.out.println(oldstamp + "=========" + oldReference);
        Thread rightStampThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "：当前的变量值："
                        + oldReference + " 当前版本戳号：" + oldstamp + asr.compareAndSet(oldReference, oldReference + "Java", oldstamp, oldstamp + 1));
            }
        });

        Thread errorStampThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String reference = asr.getReference();
                System.out.println(Thread.currentThread().getName() + "：当前的变量值："
                        + oldReference + " 当前版本戳号：" + oldstamp + asr.compareAndSet(reference, reference + "C", oldstamp, oldstamp + 1));
            }
        });
        rightStampThread.start();
        rightStampThread.join();
        errorStampThread.start();
        errorStampThread.join();

    }
}
