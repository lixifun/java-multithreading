package t20;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * reentrantlock 用于替代　synchronized
 * 本例中由于　m1 锁定　this，只有　m1 执行完毕的时候，m2 才能执行
 * 这里是复习　synchronized 最原始的语义
 * <p>
 * 使用　reentrantlock 可以完成同样的功能
 * 需要注意的是，必须要必须要必须要手动释放锁
 * 使用　synchronized 锁定的话，如果遇到异常，jvm 会自动释放锁
 * 但是　lock 必须手动释放锁，因此经常在　finally 中进行锁的释放
 */
public class ReentrantLock2 {
    Lock lock = new ReentrantLock();

    void m1() {
        try {
            lock.lock(); // synchronized (this)
            for (int i = 0; i < 10; i++) {

                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void m2() {
        lock.lock();
        System.out.println("m2...");
        lock.unlock();
    }

    public static void main(String[] args) {
        ReentrantLock2 r2 = new ReentrantLock2();
        new Thread(r2::m1).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(r2::m2).start();
    }
}
