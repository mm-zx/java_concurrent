package ren.improve.demo.vo;

import ren.improve.demo.CheckJobProcesser;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/9/2 14:53
 * @description： 提交给框架执行的工作（Job）的实体类， 工作代表了本批次需要处理的同性质任务（Task）的一个集合
 */
public class JobInfo<R> {
  // 工作名，用来区分框架中唯一的工作
  private final String jobName;
  // 记录当前工作中需要进行处理的任务数
  private final int jobLength;
  // 处理工作中任务的处理器
  private final ITaskProcesser<?, ?> taskProcesser;
  // 任务的成功次数
  private AtomicInteger successCount;
  // 工作中任务目前已经处理的次数
  private AtomicInteger taskProcessCount;
  // 存放每个任务的处理结果，供查询用
  private LinkedBlockingDeque<TaskResult<R>> taskDetailQueus;
  // 保留的工作的结果信息供查询的时间
  private final long expireTime;

  public JobInfo(
      String jobName, int jobLength, ITaskProcesser<?, ?> taskProcesser, long expireTime) {
    this.jobName = jobName;
    this.jobLength = jobLength;
    successCount = new AtomicInteger(0);
    taskProcessCount = new AtomicInteger(0);
    taskDetailQueus = new LinkedBlockingDeque<>(jobLength);
    this.taskProcesser = taskProcesser;
    this.expireTime = expireTime;
  }

  public int getSuccessCount() {
    return successCount.get();
  }

  public int getTaskProcessCount() {
    return taskProcessCount.get();
  }

  // 获取工作中失败的次数
  public int getFailCount() {
    return taskProcessCount.get() - successCount.get();
  }

  public ITaskProcesser<?, ?> getTaskProcesser() {
    return taskProcesser;
  }

  // 提供工作的整体进度信息
  public String getTotalProcess() {
    return "Success ["
        + successCount.get()
        + "] Current["
        + taskProcessCount.get()
        + "] Total["
        + jobLength
        + "]";
  }

  public int getJobLength() {
    return jobLength;
  }

  // 提供每个人物的处理结果
  public List<TaskResult<R>> getTaslkDetail() {
    List<TaskResult<R>> taskDetailList = new LinkedList<>();
    TaskResult<R> taskResult;
    // 循环的从队列获取
    while ((taskResult = taskDetailQueus.pollFirst()) != null) {
      taskDetailList.add(taskResult);
    }
    return taskDetailList;
  }

  // 每个任务完成以后，记录这个任务的处理结果
  // 从业务角度考虑，并不要求查询的数据强一致性，只需保证最终一致性，方法斌不需要加锁
  public void addTaskResult(TaskResult<R> result, CheckJobProcesser checkJob) {
    if (result.getResultType().equals(TaskResultType.Success)) {
      successCount.incrementAndGet();
    }
    taskProcessCount.incrementAndGet();
    taskDetailQueus.addLast(result);
    // 定期清除工作的结果信息
    if (taskProcessCount.get() == jobLength) {
      checkJob.putJob(jobName, expireTime);
    }
  }
}
