package ren.improve.future;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/22 17:23
 * @description： 演示Future的使用
 */
public class UseFutrue {
    //实现Callable接口  允许有返回值
    private static class UseCallable implements Callable<Integer> {
        private int sum;

        @Override
        public Integer call() throws Exception {
            System.out.println("Callable 子线程开始计算");
            Thread.sleep(2000);
            for (int i = 0; i < 5000; i++) {
                sum += i;
            }
            return sum;
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        UseCallable useCallable = new UseCallable();
        FutureTask<Integer> futureTask = new FutureTask<>(useCallable);//将callable包装成runnable
        new Thread(futureTask).start();//交给线程去执行
        Random r = new Random();
        Thread.sleep(1000);
        if(r.nextBoolean()){//根据随机数判定当前任务是否结束
            System.out.println("Get useCallable result = " + futureTask.get());
        }else {
            System.out.println("中断计算过程");
            futureTask.cancel(true);
        }
    }
}
