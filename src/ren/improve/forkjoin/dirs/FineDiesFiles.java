package ren.improve.forkjoin.dirs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/20 17:05
 * @description： 异步机制寻找指定类型文件
 */
public class FineDiesFiles extends RecursiveAction {
    private File path;//查询文件
    private String type;//寻找的文件类型

    private FineDiesFiles(File path, String type) {
        this.path = path;
        this.type = type;
    }

    @Override
    protected void compute() {
        List<FineDiesFiles> subTasks = new ArrayList<>();//子目录任务容器
        File[] files = path.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {//文件为目录的话则创建子task
                    subTasks.add(new FineDiesFiles(file, type));
                } else {
                    if (file.getName().endsWith(type)) {
                        System.out.println("find file in ：" + file.getAbsolutePath());
                    }
                }
            }
        }
        if (!subTasks.isEmpty()) {
            for (FineDiesFiles subTask : invokeAll(subTasks)) {
                subTask.join();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        FineDiesFiles totalTask = new FineDiesFiles(new File("D:/"), ".txt");
        forkJoinPool.execute(totalTask);//异步
        Thread.sleep(50);
        System.out.println("I am ok");

        totalTask.join();//是一个阻塞方法
        System.out.println("task ended");
    }
}
