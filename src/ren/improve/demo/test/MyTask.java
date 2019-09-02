package ren.improve.demo.test;

import ren.improve.demo.vo.ITaskProcesser;
import ren.improve.demo.vo.TaskResult;
import ren.improve.demo.vo.TaskResultType;

import java.util.Random;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/9/2 16:50
 * @description： 一个实际任务类，将数值加上一个随机数，并休眠随机时间
 */
public class MyTask implements ITaskProcesser<Integer, Integer> {
  @Override
  public TaskResult<Integer> taskExecute(Integer data) {
    Random r = new Random();
    int flag = r.nextInt(500);
    try {
      Thread.sleep(flag);
      if (flag <= 300) { // 正常处理情况
        Integer retrunValue = data + flag;
        return new TaskResult<>(TaskResultType.Success, retrunValue);
      } else if (flag > 301 && flag <= 400) { // 处理失败的情况
        return new TaskResult<>(TaskResultType.Failure, -1, "Failure");
      } else { // 发生异常的情况
        throw new RuntimeException("异常发生了！！！");
      }
    } catch (Exception e) {
      return new TaskResult<>(TaskResultType.Exception, -1, e.getMessage());
    }
  }
}
