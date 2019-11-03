package t10;

import java.util.concurrent.TimeUnit;

/**
 * 语法现象：
 * 一个同步方法可以调用另外一个同步方法，一个线程已经拥有某个对象的锁，再次申请时仍然会得到该对象的锁
 * 也就是说 synchronized 获得的锁是可重入锁
 * 这里是继承中有可能发生的情形，子类调用父类的同步方法
 * 锁定的是同一个对象 this
 */
public class T {

    synchronized void m() {
        System.out.println("m start");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m end");
    }

    public static void main(String[] args) {
        // 锁定的是同一个对象 this
        new TT().m();
    }
}

class TT extends T {
    @Override
    synchronized void m() {
        System.out.println("child m start");
        // 调用父类的同步方法
        super.m();
        System.out.println("child m end");
    }
}
