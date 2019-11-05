package t25;

import java.util.*;

public class T03SynchronizedList {

    public static void main(String[] args) {
        List<String> str = new ArrayList<>();
        List<String> str2 = new ArrayList<>();
        List<String> stringSync = Collections.synchronizedList(str2);

        Thread[] ths = new Thread[100];

        Random r = new Random();
        for (int i = 0; i < ths.length; i++) {
            ths[i] = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    str.add("" + r.nextInt(10000));
                    stringSync.add("" + r.nextInt(10000));
                }
            });
        }

        Arrays.stream(ths).forEach(Thread::start);
        Arrays.stream(ths).forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(str.size());
        System.out.println(stringSync.size());
    }
}
