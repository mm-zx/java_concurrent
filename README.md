## java 并发编程

### 调用方法对锁的影响

- yield(): 不会释放当前线程持有的锁。
- sleep(): 不会释放当前线程持有的锁 。
- wait(): 调用前需要持有锁，调用了 wait() 方法以后，锁会被释放。
- notify()：调用前需要持有锁，调用了 notify() 方法结束以后，锁会被释放。

### 并发工具类

#### Fork-Join     

- 功能：将一个问题，分解为k个小问题。（递归的解决小问题，小问题的解合并。）
- 条件：子问题的相互独立，子问题和原问题的形式相同。
- **RecursiveAction**：表示计算任务没有结果返回。
- **RecursiveTask**：表示计算任务有结果返回。

##### 相关方法:

- join()：阻塞方法，待task全部执行完成后执行后续部门。
- invoke(): 同步机制执行task。
- execute():异步机制执行task；注：若不使用join阻塞住，可能会导致main结束后task提前灭亡。

### 常用并发工具类

- CountDownLatch:闭锁

  - new CountDownLatch(int count) ：实例化时设置好统计个数；
  - countDown()：某个线程完成相关操作将count-1；
  - await()：阻塞方法；当count为0时释放阻塞；执行后续代码。

- CyclicBarrier：

  - new CyclicBarrier(int parties, Thread/Runnable  action)：设置一个屏障当await()执行了parties或parties的倍数次之后执行action。
  - await()：计数器加1；当计数器数量为parties或parties的倍数时执行指定的action

- Semaphore:

  - new Semaphore(int size)：实例化时设置好许可运行的线程数；
  - acquire()；获取Semaphore的运行许可，没有则排队等候(阻塞方法)
  - release(); 归还运行的许可，当Semaphore中有排队等候许可的线程，则赋予线程运行许可
  - getQueueLength()：当前正在排队等候许可的线程数
  - availablePermits();当前空闲的许可证数量；

- Exchanger：交换器

  - new Exchanger<T>(): T:存储类型
  - exchange()：交换资源（阻塞方法）；找不到需要交换的数据会一直阻塞住

  

  