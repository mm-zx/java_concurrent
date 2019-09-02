package ren.improve.demo;

import ren.improve.demo.vo.ITaskProcesser;
import ren.improve.demo.vo.JobInfo;
import ren.improve.demo.vo.TaskResult;
import ren.improve.demo.vo.TaskResultType;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/9/2 16:10
 * @description： 框架的主体类，也是调用者主要使用的类
 */
public class PendingJobPool {
  // 框架运行的线程数
  private static final int THREAD_COUNTS = Runtime.getRuntime().availableProcessors();
  // 队列，供线程池使用
  private static BlockingQueue<Runnable> taskQueue = new ArrayBlockingQueue<>(5000);
  // 线程池,固定线程池的大小，还要固定队列大小
  private static ExecutorService taskExecutor =
      new ThreadPoolExecutor(THREAD_COUNTS, THREAD_COUNTS, 60, TimeUnit.SECONDS, taskQueue);
  // 工作信息的保存的容器
  private static ConcurrentHashMap<String, JobInfo<?>> jobInfoMap = new ConcurrentHashMap<>();
  // 检查过期工作的处理
  private static CheckJobProcesser checkJob = new CheckJobProcesser(new DelayQueue<>());

  // 私有构造函数，做一些初始化的工作
  private PendingJobPool() {}

  // 构建框架，初始化
  public static PendingJobPool buildPool() {
    PendingJobPool pool = new PendingJobPool();
    checkJob.initCheck(jobInfoMap);
    return pool;
  }

  // 将工作中的任务进行包装，提交给线程池去执行,执行接口的方法，处理任务的结果写入缓存
  private static class PendingTask<T, R> implements Runnable {

    private JobInfo<R> jobInfo;
    private T processData;

    public PendingTask(JobInfo<R> jobInfo, T processData) {
      this.jobInfo = jobInfo;
      this.processData = processData;
    }

    @Override
    public void run() {
      R r = null;
      ITaskProcesser<T, R> taskProcesser = (ITaskProcesser<T, R>) jobInfo.getTaskProcesser();
      TaskResult<R> result = null;
      try {
        result = taskProcesser.taskExecute(processData);
        if (null == result) {
          result = new TaskResult<R>(TaskResultType.Exception, r, "result is null");
        }
        if (result.getResultType() == null) {
          if (result.getReason() == null) {
            result = new TaskResult<R>(TaskResultType.Exception, r, "result is null");
          } else {
            result =
                new TaskResult<R>(
                    TaskResultType.Exception, r, "result is null; reason:" + result.getReason());
          }
        }
      } finally {
        jobInfo.addTaskResult(result, checkJob);
      }
    }
  }
  // 调用者提交工作中的任务的方法
  public <T, R> void putTask(String jobName, T t) {
    JobInfo<R> jobInfo = getJob(jobName);
    PendingTask<T, R> task = new PendingTask<>(jobInfo, t);
    taskExecutor.execute(task);
  }

  // 调用注册工作的方法,工作名，工作中任务的处理器
  public <R> void registerJob(
      String jobName, int jobLength, ITaskProcesser<?, ?> taskProcesser, long expriTime) {
    JobInfo<R> jobInfo = new JobInfo<>(jobName, jobLength, taskProcesser, expriTime);
    if (jobInfoMap.putIfAbsent(jobName, jobInfo) != null) {
      throw new RuntimeException(jobName + "以及注册的任务");
    }
  }
  // 根据工作名称检索工作
  private <R> JobInfo<R> getJob(String jobName) {
    JobInfo<R> jobInfo = (JobInfo<R>) jobInfoMap.get(jobName);
    if (null == jobInfo) {
      throw new RuntimeException(jobName + "是非法任务！");
    }
    return jobInfo;
  }
  // 获得每个人物的处理详情
  public <R> List<TaskResult<R>> getTaskDetail(String jobName) {
    JobInfo<R> jobInfo = getJob(jobName);
    return jobInfo.getTaslkDetail();
  }
  // 获得工作的整体处理进度
  public <R> String getTaskProgess(String jobName) {
    JobInfo<R> jobInfo = getJob(jobName);
    return jobInfo.getTotalProcess();
  }
}
