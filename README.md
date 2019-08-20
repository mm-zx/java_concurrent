## java 并发编程

### 调用方法对锁的影响

- yield(): 不会释放当前线程持有的锁。
- sleep(): 不会释放当前线程持有的锁 。
- wait(): 调用前需要持有锁，调用了 wait 方法以后，锁会被释放。
- notify()：调用前需要持有锁，调用了 notify() 方法结束以后，锁会被释放。

### 并发工具类

#### Fork-Join

​		功能：将一个问题，分解为k个小问题。（递归的解决小问题，小问题的解合并。）

​		条件：子问题的相互独立，子问题和原问题的形式相同。

​		**RecursiveAction**：表示计算任务没有结果返回。

​		**RecursiveTask**：表示计算任务有结果返回。

##### 相关方法:

- join()：阻塞方法，待task全部执行完成后执行后续部门。
- invoke(): 同步机制执行task。
- execute():异步机制执行task；注：若不适用join阻塞住，可能会导致main结束后task提前灭亡。