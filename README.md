## java 并发编程

#### 调用yield()、sleep()、wait()、notify()等方法对锁有何影响

- yield(): 不会释放当前线程持有的锁
- sleep(): 不会释放当前线程持有的锁
- wait(): 调用前需要持有锁，调用了 wait 方法以后，锁会被释放
- notify()：调用前需要持有锁，调用了 notify() 方法结束以后，锁会被释放
