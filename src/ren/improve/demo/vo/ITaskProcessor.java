package ren.improve.demo.vo;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/9/2 14:48
 * @description： 要求框架的使用者实现这个任务接口。 因为任务的性质只有在具体业务编码时才知道，所以讲方法的参数和返回值都定义为泛型
 */
public interface ITaskProcessor<T, R> {
  TaskResult<R> taskExecute(T data);
}
