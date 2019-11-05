package t26;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class T09SingleThreadPool {

    public static void main(String[] args) {
        // 保证任务顺序执行
        ExecutorService service = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            final int j = i;
            service.execute(() -> System.out.println(j + " " + Thread.currentThread().getName()));
        }
    }
}
