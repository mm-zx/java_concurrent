package ren.improve.delayOrderDemo;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/30 16:37
 * @description： 存放的队列的元素
 */
public class ItemVo<T> implements Delayed {

    private long timeout;//到期时间,单位毫秒
    private T data;

    public ItemVo(long timeout, T data) {
        super();
        this.timeout = TimeUnit.NANOSECONDS.convert(timeout, TimeUnit.MILLISECONDS) + System.nanoTime();
        this.data = data;
    }

    public long getTimeout() {
        return timeout;
    }

    public T getData() {
        return data;
    }

    //返回元素的剩余时间
    @Override
    public long getDelay(TimeUnit unit) {
        return this.timeout - System.nanoTime();
    }

    //按照元素的剩余时间排序
    @Override
    public int compareTo(Delayed o) {
        long d = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        return (d == 0) ? 0 : (d > 0 ? 1 : -1);
    }
}
