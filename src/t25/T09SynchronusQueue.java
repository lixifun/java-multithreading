package t25;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class T09SynchronusQueue {
    public static void main(String[] args) throws InterruptedException {
        // 容量为 0 的队列
        BlockingQueue<String> strs = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                System.out.println(strs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        strs.put("aaa"); // 阻塞等待消费者消费
//        strs.add("aaa"); // 出错

        System.out.println(strs.size());
    }
}
