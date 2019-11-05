package t24;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TicketSeller4 {

    static Queue<String> tickets = new ConcurrentLinkedQueue<>();

    static {
        for (int i = 0; i < 1000; i++) {
            tickets.add("票编号 " + i);
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {

            new Thread(() -> {
                while (true) {
                    // poll 往外拿一个数据，同步操作，没拿到则为 null
                    // CAS 实现
                    // 判断以后没有对队列进行修改操作
                    String s = tickets.poll();
                    if (s == null) break;
                    else System.out.println(Thread.currentThread().getName() + " 销售了 -- " + s);
                }
            }, "窗口 " + i).start();
        }
    }
}
