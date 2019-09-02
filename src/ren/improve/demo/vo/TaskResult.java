package ren.improve.demo.vo;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/9/2 14:41
 * @description： 记录任务处理后返回的结果--实体类
 */
public class TaskResult<R> {
  private final TaskResultType resultType; // 方法是否成功完成
  private final R resultValue; // 方法处理以后的业务数据
  private final String reason; // 如果方法失败，填写失败的原因

  public TaskResult(TaskResultType resultType, R resultValue, String reason) {
    super();
    this.resultType = resultType;
    this.resultValue = resultValue;
    this.reason = reason;
  }

  public TaskResult(TaskResultType resultType, R resultValue) {
    this.resultType = resultType;
    this.resultValue = resultValue;
    this.reason = "Success";
  }

  public TaskResultType getResultType() {
    return resultType;
  }

  public R getResultValue() {
    return resultValue;
  }

  public String getReason() {
    return reason;
  }

  @Override
  public String toString() {
    return "TaskResult [resultType= "
        + resultType
        + "] "
        + ",[returnValue="
        + resultValue
        + "],"
        + " [ reason = "
        + reason
        + "]";
  }
}
