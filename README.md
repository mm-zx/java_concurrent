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

#### CAS（Compare And Swap  比较并且交换）

- 方式：CAS通过比较一个内存地址，以及一个期望的旧值。通过则替换上新值
- 常见的问题：
  - ABA：若旧值为A，在进行比较时 ，旧值已经发生如下改变 A->B->A。这样会导致比较通过，但明显不是我们预想的流程。解决方法：利用版本号:1A->2B->3A 而我们的旧值是1A，则没有通过比较。（AtomicStampedReference（））
  - 循环的时间长且开销大
  - 往往只能保证一个共享变量的原子操作

#### Jdk中相关原子类

1. 更新基本类：AtomicBoolean、AtomicInteger、AtomicLong、AtomicReference
2. 更新数组类：AtomicIntegerArray、AtomicLongArray、AtomicReferenceArray
3. 更新引用类型：AtomicReference、AtomicMarkableReference、AtomicStampedReference
4. 原子更新字段类：AtomicReferenceFieldUpdater、AtomicIntegerFieldUpdater、AtomicLongFieldUpdater

### 线程池

- ##### 为什么要用线程池

  1. 降低我们的资源消耗；
  2. 提高我们的相应速度：T1: 线程创建的时间；T2：任务运行的时间；T3：销毁线程。（使用线程池减少T1和T3）
  3. 提高线程的可管理性：使用线程池可以统一应用，调优和监控线程池内的线程

- ##### 线程池的工作机制（当主线程向线程池提交任务，线程池的执行顺序）

  1. 若当前线程数小于corePoolSize时，线程池就会启动新的线程执行任务。
  2. 若线程数已经达到corePoolSize时，任务则会被推进阻塞队列里。
  3. 当阻塞队列被推满以后，线程池就会继续增加线程处理任务，直到线程数达到了maximumPoolSIze为止。
  4. 当线程池已经存不下任务时，这时候会执行预设好的饱和策略对任务进行处理。

- ##### 为什么线程池不直接将任务推送到任务队列中，让执行线程直接从任务队列中拿

  - 采用execute()方法往线程池中推送新任务的时候，有可能会出现全局锁的情况，而使用现有机制可以保证，推送任务进队列中时(全局锁中),已有线程在执行任务，加强使用效率。

  

#### Jdk提供的线程池

- ThreadPoolExecutor

  - corePoolSize ：核心线程数
  - maximumPoolSIze: 允许的最大线程数
  - keepAliveTime：空闲的线程存活的时间
  - TimeUnit：keepAliveTime对应的时间单位
  - workQueue：阻塞队列
  - threadFactory：线程工厂，缺省的线程的命名规则：pool+数字+thread-数字
  - RejectedExecutionHandler:饱和策略（当阻塞队列满了，没有空闲的工作线程的时候，往线程池内提交任务时执行的策略）
    - AbortPolicy：直接报出异常，默认
    - CallerRunsPolicy：用调用者所在的线程执行任务。
    - DiscardOldestPolicy：丢弃阻塞队列中最老的任务，并执行目前的新任务
    - DiscardPolicy：直接丢弃当前提交的任务

- FixedThreadPool：使用固定线程数的线程池

  - LinkedBlockingQueue：无界队列，饱和策略在此失效，若任务数过多，易造成内存泄漏

- SingleThreadExecutor：单个线程数

  - LinkedBlockingQueue

- CachedThreadPool缓冲线程池(使用场景：每一个任务执行的时间都非常短)

  - SynchronousQueue：不存放任何数据；CachedThreadPool的线程数很高，不需要存放数据。

- WorkStealingPool(1.7以后)工作密取的线程；实际使用的是ForkJoinPool

- ScheduledThreadPoolExecutor:

  - ScheduledThreadPoolExecutor: 包含若干个线程的ScheduledThreadPoolExecutor

  - SingleThreadScheduledExecutor: 只包含一个线程的 ScheduledThreadPoolExecutor

  - 提交定时任务的四个方法:

    ```java
    //提交一个仅执行一次的任务
    public ScheduledFuture<?> schedule(Runnable command,long delay,TimeUnit unit)
    //提交一个仅执行一次的任务 有返回值
    public <V> ScheduledFuture<V> schedule(Callable<V> callable,long delay,TimeUnit unit) 
    //固定时间间隔循环执行
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command,long initialDelay,long period,TimeUnit unit)
    //固定延时间隔循环执行（当前线程执行完，延时固定的时间再次执行）
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command,long initialDelay,long delay,TimeUnit unit)    
    ```

  - scheduleAtFixedRate 定时任务超时问题

    - 若任务抛出异常并没有捕获处理，会导致循环终止

    - 假设任务间隔时间 60s

      1. 第一个任务 80s; 0---80s
      2. 第二个任务 20s；80s---100s
      3. 第三个任务 50s； 140s（80+60）-190s

      可以看出：若任务时长超过计划间隔时间会在上一个任务执行完立即执行下一个任务。