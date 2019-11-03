package t15;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 仅仅是简单的　++ -- 的替代方式
 * 解决同样的问题的更高效的方法，使用　AtomXXX 类，不需要　synchronized
 * AtomicXXX 比　synchronized 更高效
 * AtomXXX 类本身方法都是原子性的，但　不能　保证多个方法连续调用是原子性的
 * <p>
 * AtomicXXX 可以保证可见性吗？可以，在　AtomicKejian 中程序可证明
 */
public class T {
//    private /*volatile*/ int count = 0;

    AtomicInteger count = new AtomicInteger(0);

    /* synchronized */ void m() {
        for (int i = 0; i < 10000; i++) {
            // count++ 非原子实现
            // 原子实现
            count.incrementAndGet();
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
