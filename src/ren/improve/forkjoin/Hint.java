package ren.improve.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/20 16:16
 * @description： Fork-Join的使用范式
 */
public class Hint {

    static class MyTask extends RecursiveAction {

        @Override
        protected void compute() {
            if (true) {//当条件满足的情况下
                //do my work
            } else {//若不满足则继续拆分
                MyTask task1 = new MyTask();
                MyTask task2 = new MyTask();
                invokeAll(task1, task2);
                task1.join();
                task2.join();
            }
        }
    }

    public static void main(String[] args) {
        MyTask totalTask = new MyTask();
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(totalTask);
    }
}
