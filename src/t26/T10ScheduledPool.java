package t26;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class T10ScheduledPool {
    public static void main(String[] args) {
        // 代替 timer timer 每次 new 一个新的线程，而 scheduledExecutor 可以复用
        ScheduledExecutorService service = Executors.newScheduledThreadPool(4);

        service.scheduleAtFixedRate(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());

        }, 0, 500, TimeUnit.MILLISECONDS);
    }
}
