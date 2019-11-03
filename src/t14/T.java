package t14;

import java.util.ArrayList;
import java.util.List;

/**
 * 对比上一个程序，可以使用 synchronized 解决，synchronized 可以保证可见性和原子性，volatile 只能保证可见性
 */
public class T {
    private /*volatile*/ int count = 0;

    synchronized void m() {
        for (int i = 0; i < 10000; i++) {
            // ++ 操作不具有原子性
            count++;
        }
    }

    public static void main(String[] args) {
        T t = new T();

        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            threadList.add(new Thread(t::m, "thread-" + i));
        }

        threadList.forEach(Thread::start);

        threadList.forEach(o -> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 输出值 10000
        System.out.println(t.count);
    }
}
