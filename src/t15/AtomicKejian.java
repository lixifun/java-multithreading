package t15;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicKejian {

    AtomicBoolean running = new AtomicBoolean(true);

    void m() {
        System.out.println("m start");
        while(running.get()) {

        }
        System.out.println("m end");
    }

    public static void main(String[] args) {

        AtomicKejian kejian = new AtomicKejian();

        new Thread(kejian::m, "m").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        kejian.running.getAndSet(false);
    }
}
