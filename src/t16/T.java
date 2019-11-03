package t16;

import java.util.concurrent.TimeUnit;

/**
 * synchronized 优化
 * 同步代码块中的语句越少越好
 * 细粒度的锁比粗粒度的锁效率高
 * 比较　m1 和　m2
 */
public class T {

    int count = 0;

    synchronized void m1() {

        // do sth need not sync
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 业务代码中只有下面这句话需要　sync，这时不应该给整个方法上锁
        count++;

        // do sth need not sync
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void m2() {

        // do sth need not sync
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 业务逻辑中只有这句话需要sync，这时不应该给整个方法上锁
        // 采用细粒度的锁
        synchronized (this) {
            count++;
        }

        // do sth need not sync
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
