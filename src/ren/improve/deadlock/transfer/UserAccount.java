package ren.improve.deadlock.transfer;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/23 13:13
 * @description： 用户账户的实体类
 */
public class UserAccount {
    private final String name;//账户名称
    private int money;//账户余额

    private final Lock lock = new ReentrantLock();

    public Lock getLock() {
        return lock;
    }

    public UserAccount(String name, int money) {
        this.name = name;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return money;
    }

    @Override
    public String toString() {
        return "UserAccount{ name='" + name + "', money='" + money + "'}";
    }

    //转入资金
    public void inMoney(int amount) {
        money += amount;
    }

    //转出资金
    public void outMoney(int amount) {
        money -= amount;
    }

}
