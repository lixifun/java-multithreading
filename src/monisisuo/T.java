package monisisuo;

import java.util.concurrent.TimeUnit;

public class T {

    private final Object a = new Object();
    private final Object b = new Object();

    public void m1() {
        synchronized (a) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            m2();
        }
    }

    public void m2() {
        synchronized (b) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            m1();
        }
    }

    public static void main(String[] args) {
        T t = new T();

        // m1 拿到 a 等着 b
        new Thread(t::m1).start();
        // m2 拿到 b 等着 a
        new Thread(t::m2).start();
    }
}
