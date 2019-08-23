package ren.improve.lock.rw;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/23 17:46
 * @description： 使用读写锁
 */
public class UseRwLock implements GoodsService {
    private GoodsInfo goodsInfo;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock getLock = lock.readLock();//读锁
    private final Lock setLock = lock.writeLock();//写锁

    public UseRwLock(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    @Override
    public GoodsInfo getNum() {
        getLock.lock();
        try {
            Thread.sleep(5);
            return this.goodsInfo;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } finally {
            getLock.unlock();
        }
    }

    @Override
    public void setNum(int number) {
        setLock.lock();
        try {
            Thread.sleep(5);
            goodsInfo.chageNumber(number);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            setLock.unlock();
        }
    }
}
