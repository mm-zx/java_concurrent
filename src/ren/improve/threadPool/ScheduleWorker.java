package ren.improve.threadPool;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/29 16:54
 * @description： 定时任务的工作类
 */
public class ScheduleWorker implements Runnable {
    public final static int Normal = 0;//普通任务类型
    public final static int HasException = -1;//会抛出异常的任务类型
    public final static int ProcessException = 1;//抛出异常但会捕捉的任务类型
    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int taskType;

    public ScheduleWorker(int taskType) {
        this.taskType = taskType;
    }

    @Override
    public void run() {
        if (taskType == HasException) {
            System.out.println(format.format(new Date()) + " Exception be made");
            throw new RuntimeException("ExceptionHappen");
        }else if(taskType == ProcessException){
            try {
                System.out.println(format.format(new Date()) + " Exception be made," +
                        "will be catch");
                throw new RuntimeException("ExceptionHappen");
            } catch (RuntimeException e) {
                System.out.println("We catch Exception!");
            }
        }else {
            System.out.println(format.format(new Date()) + " Normal----------");
        }
    }
}
