package t04;

/**
 * synchronized 用在静态方法上，相当于锁定 t04.T.class
 */
public class T {

    private static int count = 10;

    public synchronized static void m() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public static void mm() {
        // 这里不可以使用 synchronized (this) 因为静态方法中不存在 this 即类的实例
        synchronized (T.class) {
            count--;
        }
    }
}
