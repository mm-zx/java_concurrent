package ren.improve.tool.semaphore;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/21 15:11
 * @description： 一个使用Semaphore实现的数据库连接池
 */
public class DBPoolSemaphore {
    private final static int POOL_SIZE = 10;//连接数量只有十个
    private static final LinkedList<Connection> pool = new LinkedList<>();//存放连接的容器
    private final Semaphore useful, useLess;//分别表示可用连接和已用连接

    //初始化池
    static {
        for (int i = 0; i < POOL_SIZE; i++) {
            pool.addLast(SqlConnectImpl.fetchConnection());
        }
    }

    public DBPoolSemaphore() {
        this.useful = new Semaphore(POOL_SIZE);
        this.useLess = new Semaphore(0);
    }

    //归还连接
    public void returnConnect(Connection connection) throws InterruptedException {
        if (connection != null) {
            System.out.println("当前有" + useful.getQueueLength() + "个线程等待数据库连接！！！可用连接数：" + useful.availablePermits());
            useLess.acquire();
            synchronized (pool) {
                pool.addLast(connection);
            }
            useful.release();
        }
    }

    //从池子拿连接
    public Connection takeConnect() throws InterruptedException {
        useful.acquire();//acquire()获取Semaphore的运行许可，没有则排队等候
        Connection connection;
        synchronized (pool) {
            connection = pool.removeFirst();
        }
        useLess.release();
        return connection;
    }

}
