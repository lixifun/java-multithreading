package t25;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;

/**
 * 对于 map/set 的选择使用
 * HashMap
 * TreeMap
 * LinkedHashMap
 * <p>
 * Hashtable
 * Collections.synchronizedXXX
 * <p>
 * ConcurrentHashMap
 * ConcurrentSkipListMap
 *
 * list 的选择
 * ArrayList
 * LinkedList
 *
 */
public class T01ConcurrentMap {

    public static void main(String[] args) {

        // 应急捷安特

//    Map<String, String> map = new ConcurrentHashMap<>();

        // 跳表结构，插入效率高，排序
//    Map<String, String> map = new ConcurrentSkipListMap<>();

//        Map<String, String> map = new Hashtable<>();

//    Map<String, String> map = new HashMap<>(); //Collections.synchronizedXXX
        Map<String, String> map = new TreeMap<>();

        Random r = new Random();
        Thread[] ths = new Thread[100];

        CountDownLatch latch = new CountDownLatch(ths.length);

        long start = System.currentTimeMillis();

        for (int i = 0; i < ths.length; i++) {
            ths[i] = new Thread(() -> {
                for (int j = 0; j < 99999; j++) {
                    map.put("a" + r.nextInt(100000), "a" + r.nextInt(100000));
                }

                latch.countDown();
            });
        }

        Arrays.stream(ths).forEach(Thread::start);

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
