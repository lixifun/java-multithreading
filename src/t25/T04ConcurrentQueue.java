package t25;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class T04ConcurrentQueue {

    public static void main(String[] args) {

        // 无界队列，内存满崩
        Queue<String> strs = new ConcurrentLinkedQueue<>();

        for (int i = 0; i < 10; i++) {
            strs.offer("a" + i); // add
        }

        System.out.println(strs);
        System.out.println(strs.size());

        // poll 拿出来，删掉
        System.out.println(strs.poll());
        System.out.println(strs.size());

        // peek 拿出来，不删除
        System.out.println(strs.peek());
        System.out.println(strs.size());

        // 双端队列
        Queue<String> stringDeque = new ConcurrentLinkedDeque<>();


    }
}
