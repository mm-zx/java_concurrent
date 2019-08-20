package ren.improve.forkjoin.dirs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/20 16:41
 * @description： 同步机制统计文件个数
 */
public class SumDirsFiles extends RecursiveTask<Integer> {
    private File path;//统计的目录

    private SumDirsFiles(File path) {
        this.path = path;
    }

    @Override
    protected Integer compute() {
        int count = 0;//文件个数的计数器
        int dirCount = 0;//目录的计数器
        List<SumDirsFiles> subTasks = new ArrayList<>();//子目录任务容器
        File[] files = path.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {//文件为目录的话则创建子task
                    subTasks.add(new SumDirsFiles(file));
                    dirCount++;
                } else {
                    count++;
                }
            }
        }
        if (!subTasks.isEmpty()) {
            for (SumDirsFiles subTask : invokeAll(subTasks)) {
                count += subTask.join();
            }
        }
        System.out.println("目录：" + path.getAbsolutePath() + "包含的子目录个数：" + dirCount + "，文件个数：" + count);
        return count;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        SumDirsFiles totalTask = new SumDirsFiles(new File("C:/"));
        forkJoinPool.invoke(totalTask);
        System.out.println("File count : " + totalTask.join());
    }
}
