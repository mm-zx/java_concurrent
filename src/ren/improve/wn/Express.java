package ren.improve.wn;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/20 14:18
 * @description： 快递类
 */
public class Express {
    final static String CITY = "shanghai";
    private int km;//快递运输里程数
    private String site;//快递到达地点

    public Express() {

    }

    Express(int km, String site) {
        this.km = km;
        this.site = site;
    }

    //变化公里数,然后通知出去wait状态下的并且需要处理公里数的线程进行业务处理
    synchronized void changeKm() {
        this.km = 101;
        notifyAll();
    }

    //变化地点,然后通知出去wait状态下的并且需要处理地点的线程进行业务处理
    synchronized void changeSite() {
        this.site = "beijing";
        //notifyAll唤醒所有处于wait状态下的线程
        notifyAll();//notify/notifyAll中尽量选择notifyAll（notify唤醒的不一定是自己所想唤醒的线程）
    }

    //当快递里程数大于100时处理数据库
    synchronized void waitKm() {
        while (this.km < 100) {
            try {
                wait();
                System.out.println("check km thread[" + Thread.currentThread().getName() + "] is be notify");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("the km is " + this.km + " I will change db");
    }

    //当快递到达目的地通知用户
    synchronized void waitSite() {
        while (this.site.equals(CITY)) {
            try {
                wait();
                System.out.println("check site thread[" + Thread.currentThread().getName() + "] is be notify");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("the site is " + this.site + " I will call user");
    }
}
