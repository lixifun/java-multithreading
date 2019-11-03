package t05;

import java.util.concurrent.TimeUnit;

public class T implements Runnable {

    private int count = 10;

    /**
     * synchronized 代码块是原子操作
     */
    @Override
    public synchronized void run() {
        count--;
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public static void main(String[] args) {
        // 栈内存里有一个 t 指向一个 new T()
        T t = new T();
        for (int i = 0; i < 5; i++) {
            // 五个线程同一个 t，即 count 为同一个
            new Thread(t, "THREAD - " + i).start();
        }
    }


    /**
     * 可能的执行结果
     * 打印之前时间片到了
     * THREAD - 1 count = 8
     * THREAD - 0 count = 8
     * THREAD - 2 count = 7
     * THREAD - 3 count = 6
     * THREAD - 4 count = 5
     */
}
