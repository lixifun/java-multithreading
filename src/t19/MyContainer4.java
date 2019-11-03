package t19;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 给　list 添加　volatile 之后，t2 能够接到通知，但是　t2 线程的死循环很浪费　cpu
 * <p>
 * 使用　wait 和　notify ，wait　会释放锁，而　notify 不会释放锁
 * 需要注意的是，运用这种方法，必须要保证　t2 先执行，也就是首先让　t2 监听才行
 * <p>
 * notify 不释放锁，所以
 * t1 notify 以后还得　wait 释放锁，让　t2　获得锁执行，t2 退出后也必须　notify，通知　t1 继续执行
 */
public class MyContainer4 {
    // 使　t2 能够得到通知
    volatile List list = new ArrayList();

    public void add(Object o) {
        list.add(o);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        MyContainer4 c = new MyContainer4();

        final Object lock = new Object();


        new Thread(() -> {
            synchronized (lock) {
                System.out.println("t2 启动");
                if (c.size() != 5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2 结束");
                //　通知　t1 继续执行
                lock.notify();
            }

        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("t1 启动");
            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    c.add(new Object());
                    System.out.println("add " + i);

                    if (c.size() == 5) {
                        // notify 不释放锁
                        lock.notify();

                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t1").start();

    }
}
