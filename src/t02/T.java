package t02;

/**
 * synchronized 关键字
 * 对某个对象加锁
 * 不是对代码块加锁
 */
public class T {

    private int count = 10;

    public void m() {
        // 可以使用 this 对象来锁定
        synchronized (this) {
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }
}
