package t26;

import java.util.concurrent.*;

public class T06Future {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<Integer> task = new FutureTask<>(() -> {
            TimeUnit.MILLISECONDS.sleep(500);
            return 1000;
        });

        new Thread(task).start();

        System.out.println(task.get()); // 阻塞

        //**************************
        ExecutorService service = Executors.newFixedThreadPool(5);
        Future<Integer> f = service.submit(() -> {
            TimeUnit.MILLISECONDS.sleep(500);
            return 1;
        });

//        System.out.println(f.get()); // 阻塞什么时候执行完
        System.out.println(f.isDone());
    }
}
