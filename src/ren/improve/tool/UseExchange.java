package ren.improve.tool;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Exchanger;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/21 16:57
 * @description： 演示Exchange用法
 */
public class UseExchange {
    private static final Exchanger<Set<String>> exchange = new Exchanger<>();

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Set<String> setA = new HashSet<>();//存放数据的容器
                try {
                    setA.add("A1");
                    setA.add("A2");
                    setA.add("A3");
                    setA.add("A4");
                    setA = exchange.exchange(setA);//交换set
                    //处理交换后的数据
                    System.out.println("Thread["+Thread.currentThread().getId()+"]+ strA:"+setA);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Set<String> setB = new HashSet<>();//存放数据的容器
                try {
                    setB.add("B1");
                    setB.add("B2");
                    setB.add("B3");
                    setB.add("B4");
                    setB = exchange.exchange(setB);//交换set
                    //处理交换后的数据
                    System.out.println("Thread["+Thread.currentThread().getId()+"]+ strB:"+setB);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Set<String> setC = new HashSet<>();//存放数据的容器
                try {
                    setC.add("C1");
                    setC.add("C2");
                    setC.add("C3");
                    setC.add("C4");
                    setC = exchange.exchange(setC);//交换set
                    //处理交换后的数据
                    System.out.println("Thread["+Thread.currentThread().getId()+"]+ strC:"+setC);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(4000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Set<String> setD = new HashSet<>();//存放数据的容器
                try {
                    setD.add("D1");
                    setD.add("D2");
                    setD.add("D3");
                    setD.add("D4");
                    setD = exchange.exchange(setD);//交换set
                    //处理交换后的数据
                    System.out.println("Thread["+Thread.currentThread().getId()+"]+ strD:"+setD);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
