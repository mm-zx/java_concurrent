package ren.improve.threadPool;

import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/29 17:02
 * @description： 执行定时任务
 */
public class ScheduledCase {
    public static void main(String[] args) {
        ScheduledThreadPoolExecutor schedule = new ScheduledThreadPoolExecutor(1);
        //只执行一次
//        schedule.schedule(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(" the task run once !");
//            }
//        }, 3000, TimeUnit.MILLISECONDS);
//        schedule.scheduleWithFixedDelay(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(" FixedDelay start :" + ScheduleWorker.format.format(new Date()));
//                try {
//                    Thread.sleep(2000);
//                    System.out.println(" FixedDelay end :" + ScheduleWorker.format.format(new Date()));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 1000, 3000, TimeUnit.MILLISECONDS);
        schedule.scheduleWithFixedDelay(new ScheduleWorker(ScheduleWorker.Normal), 1000, 3000, TimeUnit.MILLISECONDS);
        schedule.scheduleWithFixedDelay(new ScheduleWorker(ScheduleWorker.HasException), 1000, 3000, TimeUnit.MILLISECONDS);
        schedule.scheduleWithFixedDelay(new ScheduleWorker(ScheduleWorker.ProcessException), 1000, 3000, TimeUnit.MILLISECONDS);
    }
}
