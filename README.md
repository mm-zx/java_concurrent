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

  - new Exchanger< T >(): T:存储类型
  - exchange()：交换资源（阻塞方法）；找不到需要交换的数据会一直阻塞住

  

### 线程安全

#### 如何定义线程安全：

​		某个类如果在多线程下访问，不管调用者如何调用这个类，这个类总可以表现出正确的行为，则这个类就是线程安全的。

#### JMM引发的问题：

- 数据不一致，缓存不一致。
- 指令重排序，（单线程下的最终结果可能没错，但多线程下结果可能并不是我们预期的）

#### 如何才能做到线程安全：

- 无状态类：类里只有一些成员方法，而无成员变量
- 让类不可变：类里的成员变量都是基本类型且被final修饰、若非基本类型 则该类必须也是不可变类才可。
- volatile: 保证类的可见性，并不能绝对做到线程安全
- 加锁：
- **CAS**
- ……

#### 更多线程不安全问题

- 死锁：是指两个或两个以上的进程在执行过程中，由于竞争资源或者由于彼此通信而造成的一种阻塞的现象，若无外力作用，他们将无法推进下去。此时称系统处于死锁状态或系统产生了死锁

#### 死锁及解决方法

- 简单的死锁：当线程相互竞争资源时，应统一获取锁的顺序。不应互相持有对方想要获取的锁资源。
- 动态的死锁：在不可控的情况下，我们可以根据锁对象的hashCode，按hashCode的大小决定获取锁的顺序，推荐使用System.identityHashCode(object)；这样也是为了与其他线程统一取锁顺序。（当出现HashCode一直时）我们可以手动生成加时锁，避免特殊情况的产生。归还加时锁即表示当前线程已结束这样边不会去其他线程产生死锁情况）

### 显示锁和CAS

#### 显示锁

- Lock与synchronized相比的优势
  - lock加锁和解锁可以显示的控制：lock()，unlock()
  - lock可以尝试非阻塞获取锁并可设置超时时间：tryLock()
  - 可以被中断的获取锁：lockInterruptibly()->"若线程执行lockInterruptibly()没有获取到锁，当前线程可以通过interrupt()中断"
- Lock的实现类
  - ReentrantLock
  - ReentrantReadWriteLock（一写多读情况下性能明显优于synchronized）
    - writeLock()//返回写锁
    - readLock()//返回读锁
- Condition接口（实现通知机制，可以有选择的操作等待与通知,可控性大于wait()和notify()/notifyAll();）
  - 获得方法：lock.newCondition()
  - 等待(阻塞方法):condition.await()//注 condition也有wait()，但这个wait是object提供的
  - 通知：condition.signal()/condition.signalAll()。通知调用了当前condition的await()线程

