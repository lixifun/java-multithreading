package t08;

import java.util.concurrent.TimeUnit;

/**
 * 对写方法加锁
 * 读方法方法不加锁
 * 容易产生脏读
 * <p>
 * 写的操作需要时间，在这段时间的读就会读取到脏值
 */
public class Account {

    String name;
    double balance;

    public synchronized void set(String name, double balance) {
        this.name = name;

        // 放大线程执行的时间差
        // 模拟有其他业务代码执行
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.balance = balance;
    }

    public double getBalance() {
        return this.balance;
    }

    public static void main(String[] args) {
        Account a = new Account();
        new Thread(() -> a.set("zhangsan", 100.0)).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 输出结果 0.0
        System.out.println(a.getBalance());

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 输出结果 100.0
        System.out.println(a.getBalance());
    }
}
