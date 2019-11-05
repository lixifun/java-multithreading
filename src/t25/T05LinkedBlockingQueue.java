package t25;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Queue
 * - ConcurrentLinkedQueue
 * <p>
 * BlockingQueue
 * - LinkedBlockingQueue
 * - ArrayBlockingQueue
 */
public class T05LinkedBlockingQueue {

    static BlockingQueue<String> strs = new LinkedBlockingQueue<>();
    // 有界队列
    // static BlockingQueue<String> strs = new ArrayBlockingQueue<>(10);
    // strs.put("a"); // 满了就等待
    // strs.add("a"); // 抛异常
    // strs.offer("a"); // 返回值判断是否成功

    static Random r = new Random();

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    strs.put("a" + i); // 如果满了就会等待
                    TimeUnit.MILLISECONDS.sleep(r.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "p1").start();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                for (; ; ) {
                    try {
                        // 如果空了就会等待
                        System.out.println(Thread.currentThread().getName() + " take - " + strs.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "c" + i).start();
        }

    }
}
