package t17;

import java.util.concurrent.TimeUnit;

/**
 * 锁的对象是　堆　上的真实对象而不是　栈　上的变量
 * 锁定对象 o，如果 o 的属性发生改变，不影响锁的使用
 * 但是如果 o 变成另外一个对象，则锁定的对象发生改变
 * 应该避免锁定对象的引用变成另外的对象
 */
public class T {
    /* final*/ Object o = new Object();

    void m() {
        synchronized (o) {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {
        T t = new T();

        // 创建第一个线程
        new Thread(t::m, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 创建第二个线程，正常情况下　t2 永远不会执行，因为　t1 从未释放锁
        Thread t2 = new Thread(t::m, "t2");

        // 锁对象发生改变，所以　t2 得以执行，如果注释掉这句话，线程 2 将永远得不到执行机会
        t.o = new Object();
        t2.start();
    }
}
