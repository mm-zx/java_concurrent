package ren.improve.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/23 16:28
 * @description： 演示使用显示锁
 */
public class UseLock {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            //todo
        } finally {
            lock.unlock();
        }
    }
}
