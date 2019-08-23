package ren.improve.deadlock.transfer.service;

import ren.improve.deadlock.transfer.UserAccount;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/23 14:16
 * @description： 线程安全的转账类
 */
public class SafeOperate implements ITransfer {
    private static final Object tieLock = new Object();//加时赛锁

    @Override
    public void transfer(UserAccount from, UserAccount to, int amount) {
        int fromHash = System.identityHashCode(from);//拿到转出账户的hash
        int toHash = System.identityHashCode(to);//拿到转入账户的hash
        //先锁小的，再锁大的
        if (fromHash < toHash) {
            synchronized (from) {//先锁转出账户
                System.out.println(Thread.currentThread().getName() + " get " + from.getName());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (to) {//再锁转入账户
                    System.out.println(Thread.currentThread().getName() + " get " + to.getName());
                    from.outMoney(amount);
                    to.inMoney(amount);
                    System.out.println(from);
                    System.out.println(to);
                }
            }
        } else if (toHash < fromHash) {
            synchronized (to) {//先锁转入账户
                System.out.println(Thread.currentThread().getName() + " get " + to.getName());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (from) {//再锁转出账户
                    System.out.println(Thread.currentThread().getName() + " get " + from.getName());
                    from.outMoney(amount);
                    to.inMoney(amount);
                    System.out.println(from);
                    System.out.println(to);
                }
            }
        } else {
            synchronized (tieLock) {
                synchronized (from) {//先锁转出账户
                    synchronized (to) {//再锁转入账户
                        from.outMoney(amount);
                        to.inMoney(amount);
                        System.out.println(from);
                        System.out.println(to);
                    }
                }
            }
        }
    }
}
