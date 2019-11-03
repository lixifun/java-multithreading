package t13;

import java.util.ArrayList;
import java.util.List;

/**
 * volatile 并不能保证多个线程共同修改 running 变量时所带来的不一致性问题，也就是说 volatile 不能代替 synchronized
 * 这里跟 t12.(1) 里说的是一回事儿了，因为这里的 count 依赖于上一次的值，并且不能保证只有一个线程在修改
 */
public class T {
    private volatile int count = 0;

    /*synchronized*/ void m() {
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

        // 输出值，随机 97977
        System.out.println(t.count);
    }
}
