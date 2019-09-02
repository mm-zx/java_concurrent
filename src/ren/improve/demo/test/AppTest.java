package ren.improve.demo.test;

import ren.improve.demo.PendingJobPool;
import ren.improve.demo.vo.TaskResult;

import java.util.List;
import java.util.Random;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/9/2 17:03
 * @description： 模拟一个应用程序，提交工作和任务，并查询任务进度
 */
public class AppTest {
  private static final String JOB_NAME = "计算数值";
  private static final int JOB_LENGTH = 1000;
  // 查询任务进度的线程
  private static class QueryResult implements Runnable {

    private PendingJobPool pendingJobPool;

    public QueryResult(PendingJobPool pendingJobPool) {
      this.pendingJobPool = pendingJobPool;
    }

    @Override
    public void run() {
      int i = 0;
      while (i < 350) {
        List<TaskResult<String>> taskDetail = pendingJobPool.getTaskDetail(JOB_NAME);
        if (!taskDetail.isEmpty()) {
          System.out.println(pendingJobPool.getTaskProgess(JOB_NAME));
          System.out.println(taskDetail);
        }
        try {
          i++;
          Thread.sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static void main(String[] args) {
    MyTask myTask = new MyTask();
    PendingJobPool pool = PendingJobPool.buildPool();
    pool.registerJob(JOB_NAME, JOB_LENGTH, myTask, 1000 * 5);
    Random r = new Random();
    for (int i = 0; i < JOB_LENGTH; i++) {
      pool.putTask(JOB_NAME, r.nextInt(1000));
    }
    Thread query = new Thread(new QueryResult(pool));
    query.start();
  }
}
