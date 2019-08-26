package ren.improve.lock.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/26 11:14
 * @description： 快递实现类
 */
public class ExpressCond {
    final static String CITY = "Shanghai";
    private int km;//快递运输里程数
    private String site;//快递到达地点
    private Lock kmLock = new ReentrantLock();
    private Lock siteLock = new ReentrantLock();
    private Condition kmCond = kmLock.newCondition();
    private Condition siteCond = siteLock.newCondition();

    public ExpressCond() {

    }

    ExpressCond(int km, String site) {
        this.km = km;
        this.site = site;
    }

    //变化公里数,然后通知出去wait状态下的并且需要处理公里数的线程进行业务处理
    void changeKm() {
        kmLock.lock();
        try {
            this.km = 101;
            kmCond.signalAll();//通知其他在锁上等待的线程
        } finally {
            kmLock.unlock();

        }
    }

    //变化地点,然后通知出去wait状态下的并且需要处理地点的线程进行业务处理
    void changeSite() {
        siteLock.lock();
        try {
            this.site = "Beijing";
            siteCond.signalAll();
        } finally {
            siteLock.unlock();
        }
    }

    //当快递里程数大于100时处理数据库
    void waitKm() {
        kmLock.lock();
        try {
            while (this.km < 100) {
                try {
                    kmCond.await();//当前线程进行等待
                    System.out.println("check km thread[" + Thread.currentThread().getName() + "] is be notify");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            kmLock.unlock();
        }

        System.out.println("the km is " + this.km + " I will change db");
    }

    //当快递到达目的地通知用户
    void waitSite() {
        siteLock.lock();
        try {
            while (this.site.equals(CITY)) {
                try {
                    siteCond.await();//当前线程进行等待
                    System.out.println("check site thread[" + Thread.currentThread().getName() + "] is be notify");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            siteLock.unlock();
        }
        System.out.println("the site is " + this.site + " I will call user");
    }
}
