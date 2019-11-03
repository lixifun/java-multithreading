package t20;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 在使用　reentrantlock 时可以进行　tryLock
 * 这样无法锁定，或者在指定时间内无法锁定，线程可以决定是否继续等待
 * <p>
 * ReentrantLock 比　synchronized 更灵活
 */
public class ReentrantLock4 {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                System.out.println("t1 start");
                TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
                System.out.println("t1 end");
            } catch (InterruptedException e) {
                System.out.println("t1 interrupted!");
            } finally {
                lock.unlock();
            }
        });

        t1.start();

        Thread t2 = new Thread(() -> {
            try {
                // lock.lock();
                // 可以对　interrupt() 方法做出响应
                lock.lockInterruptibly();
                System.out.println("t2 start");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("t2 end");

            } catch (InterruptedException e) {
                System.out.println("t2 interrupted!");
            } finally {
                lock.unlock();
            }
        });
        t2.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 打断线程 2 的等待
        t2.interrupt();
    }
}
