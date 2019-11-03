package t15;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomXXX 多个操作的非原子性
 * <p>
 * 输出值 有一定几率输出　1001
 */
public class MultiAtomicOperator {
    //    private /*volatile*/ int count = 0;

    AtomicInteger count = new AtomicInteger(0);

    /* synchronized */ void m() {
        for (int i = 0; i < 10000; i++) {
            // 多个原子操作的组合不一定是原子操作
            if (count.get() < 1000) {
                // 这个地方，另外的线程有可能进来
                // 此时　999 两个线程都进到了这个地方，最终结果就成了　1001
                count.incrementAndGet();
            }
        }
    }

    public static void main(String[] args) {
        MultiAtomicOperator t = new MultiAtomicOperator();

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

        // 输出值 有一定几率输出　1001
        System.out.println(t.count);
    }
}
