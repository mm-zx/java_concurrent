package ren.improve.demo;

import ren.improve.delayOrderDemo.ItemVo;
import ren.improve.demo.vo.JobInfo;

import java.util.Map;
import java.util.concurrent.DelayQueue;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/9/2 15:25
 * @description： 任务完成以后，保留一段时间供查询，为了节省资源，我们要定期清理过期的任务
 */
public class CheckJobProcessor {
  private DelayQueue<ItemVo<String>> queue; // 存放任务的队列

  public CheckJobProcessor(DelayQueue<ItemVo<String>> queue) {
    this.queue = queue;
  }

  // 处理队列中到期任务的线程
  private static class FetchJob implements Runnable {
    private DelayQueue<ItemVo<String>> queue;
    private Map<String, JobInfo<?>> jobInfoMap;

    public FetchJob(DelayQueue<ItemVo<String>> queue, Map<String, JobInfo<?>> jobInfoMap) {
      this.queue = queue;
      this.jobInfoMap = jobInfoMap;
    }

    @Override
    public void run() {
      while (true) {
        try {
          ItemVo<String> itemVo = queue.take();
          String jobName = itemVo.getData();
          jobInfoMap.remove(jobName); // 从缓存中移除过期的任务
          System.out.println("Job: [" + jobName + "] is out of data,remove from JobList");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /*任务完成后，放入队列，经过expireTime时间后，从整个框架中移除*/
  public void putJob(String jobName, long expireTime) {
    ItemVo<String> item = new ItemVo<>(expireTime, jobName);
    queue.offer(item);
    System.out.println("Job [" + jobName + "]已经放入过期检查缓存，时长：" + expireTime);
  }

  /*任务过期的检查线程，设置守护线程*/
  public void initCheck(Map<String, JobInfo<?>> jobInfoMap) {
    Thread thread = new Thread(new FetchJob(queue, jobInfoMap));
    thread.setDaemon(true);
    thread.start();
    System.out.println("开启了任务过期的检查守护线程");
  }
}
