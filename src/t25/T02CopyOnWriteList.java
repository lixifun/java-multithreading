package t25;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 写时复制容器
 * 读的时候不用加锁
 * 实际例子：
 * 事件监听器的队列
 */
public class T02CopyOnWriteList {

    public static void main(String[] args) {

        List<String> lists =
//                 new ArrayList<>(); // 这个会出并发问题
//                new Vector<>();
                new CopyOnWriteArrayList<>();

        Random r = new Random();
        Thread[] ths = new Thread[100];

        for (int i = 0; i < ths.length; i++) {

            ths[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    lists.add("a" + r.nextInt(10000));
                }
            });
        }

        runAndComputeTime(ths);
        System.out.println(lists.size());
    }

    private static void runAndComputeTime(Thread[] ths) {
        long start = System.currentTimeMillis();

        Arrays.stream(ths).forEach(Thread::start);
        Arrays.stream(ths).forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

}
