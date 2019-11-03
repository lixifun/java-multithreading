package t03;

/**
 * synchronized 关键字
 * <p>
 * 方法进入时直接锁定 this ，即 t02.T.m() 里的则可以简写为下面的形式
 * 将 synchronized 直接加在方法上
 */
public class T {

    private int count = 10;

    // 等同于在方执行时 synchronized (this)
    public synchronized void m() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }
}
