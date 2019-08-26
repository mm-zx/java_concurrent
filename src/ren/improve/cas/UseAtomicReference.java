package ren.improve.cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/26 15:20
 * @description： 演示应用的类型原子操作
 */
public class UseAtomicReference {
    static AtomicReference<UserInfo> atomicUserRef = new AtomicReference<>();

    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo("zhou", 24);
        atomicUserRef.set(userInfo);
        UserInfo updateUserInfo = new UserInfo("improve", 20);
        atomicUserRef.compareAndSet(userInfo, updateUserInfo);//param1:期望旧值，param2：替换新值
        System.out.println(atomicUserRef.get().getName());
        System.out.println(atomicUserRef.get().getAge());
    }

    //实体类
    static class UserInfo {
        private String name;
        private int age;

        public UserInfo(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
