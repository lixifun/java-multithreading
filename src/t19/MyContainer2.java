package t19;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyContainer2 {
    // 对比　MyContainer1 仅仅加了　volatile
    // 使　t2 能够得到通知
    volatile List list = new ArrayList();

    public void add(Object o) {
        list.add(o);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        MyContainer2 c = new MyContainer2();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                c.add(new Object());
                System.out.println("add " + i);

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        new Thread(() -> {
            // 很浪费　cpu
            while (true) {
                if (c.size() == 5) {
                    // 执行结果不够精确
                    break;
                }
            }
            System.out.println("t2 结束");

        }, "t2").start();
    }
}
