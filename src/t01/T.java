package t01;

/**
 * Java 高并发三块内容：
 * 1. synchronized 同步器
 * 2. 同步容器
 * 3. ThreadPool executor
 * <p>
 * synchronized 关键字
 * 对某个对象加锁
 */
public class T {

    private int count = 10;
    private final Object o = new Object();

    public void m() {
        // 任何线程要执行下面的代码，必须先拿到 o 的锁
        // 互斥锁
        synchronized (o) {
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }
}
