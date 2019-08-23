package ren.improve.deadlock.transfer.service;

import ren.improve.deadlock.transfer.UserAccount;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/23 13:22
 * @description： 不安全的转账动作的实现
 */
public class TransferAccount implements ITransfer {
    @Override
    public void transfer(UserAccount from, UserAccount to, int amount) {
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
            }
        }
    }
}
