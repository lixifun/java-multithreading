package t19;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 给　list 添加　volatile 之后，t2 能够接到通知，但是　t2 线程的死循环很浪费　cpu
 * <p>
 * 使用　wait 和　notify ，wait　会释放锁，而　notify 不会释放锁
 * 需要注意的是，运用这种方法，必须要保证　t2 先执行，也就是首先让　t2 监听才行
 * <p>
 * notify 不释放锁，所以
 * t1 notify 以后还得　wait 释放锁，让　t2　获得锁执行，t2 退出后也必须　notify，通知　t1 继续执行
 * 整个通信过程比较繁琐
 * <p>
 * 使用　Latch (门闩) 替代 wait notify 来进行通知
 * 好处是通信方式简单，同时也可以指定等待时间
 * 使用　await 和　countDown 方法替代　wait 和　notify
 * CountDownLatch 不涉及锁定，当　count 值为　０　时当前线程继续运行
 * 当不涉及同步，只是涉及线程通信的时候，用　synchronized + wait/notify 就显得重了
 * 这时应该考虑　countDownLatch/cyclicbarrier/semaphore
 */
public class MyContainer5 {
    // 使　t2 能够得到通知
    volatile List list = new ArrayList();

    public void add(Object o) {
        list.add(o);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        MyContainer5 c = new MyContainer5();

        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            System.out.println("t2 启动");
            if (c.size() != 5) {
                try {
                    // 门闩的等待不需要锁定任何东西的
                    latch.await();

                    // 也可以指定等待时间
//                    latch.await(5000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("t2 结束");

        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("t1 启动");
            for (int i = 0; i < 10; i++) {
                c.add(new Object());
                System.out.println("add " + i);

                if (c.size() == 5) {
                    // 打开门闩，让　t2 得到执行
                    latch.countDown();
                }

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

    }
}
