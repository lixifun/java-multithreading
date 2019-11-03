package t20;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 可以指定为公平锁，谁等的时间长，谁先拿到锁
 * 默认　synchronized 为不公平锁
 */
public class ReentrantLock5 extends Thread {
    // fair 表示为公平锁
    private static ReentrantLock lock = new ReentrantLock(true);

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "获得锁 " + i);
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLock5 rl = new ReentrantLock5();
        Thread t1 = new Thread(rl);
        Thread t2 = new Thread(rl);
        t1.start();
        t2.start();
    }
}
