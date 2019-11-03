package t12;

import java.util.concurrent.TimeUnit;

/**
 * volatile 可见性，无锁同步
 * <p>
 * 《Java 并发编程实践》p32
 * 加锁机制既可以确保可见性又可以确保原子性，而 volatile 变量只能确保可见性
 * 当且仅当满足一下所有条件时，才可以使用 volatile 变量:
 * 1. 对变量的写入操作不依赖当前变量的当前值，或者可以确保只有单个线程更新变量的值
 * 2. 该变量不会与其他状态变量一起纳入不变性条件中
 * 3. 在访问变量时不需要加锁
 * <p>
 * 在此例子中， running 变量的改变，不依赖于当前值，而且只有单个线程更新
 * （对于此例子来说，可以有多个线程同时改变 running
 * 因为 running 不依赖于当前变量的当前值）
 * <p>
 * JMM (java memory model) Java 内存模型
 * 内存：真实内存，cpu 缓冲区 等区域 (尤其注意多核 cpu，多个 cpu)
 * 每一个线程的执行过程中，从主内存 copy 一份 running 到自己的缓冲区，以后就不读了
 * （不绝对，看 cpu 调度，cpu 有可能会空出来读一下主内存，不可预期行为）
 * <p>
 * volatile 并不能保证多个线程共同修改 running 变量时所带来的不一致性问题，也就是说 volatile 不能代替 synchronized
 * 上面这句话的感觉跟 (1) 说的有冲突，这里的 running 的修改如果是多个线程，但是没有依赖当前值，是不会有不一致性问题的
 * <p>
 * Java 中线程间通讯，只有一种方式：共享内存，大家都去读同一块内存
 */
public class T {
    // 对比一下有无 volatile 的情况，整个程序运行结果的区别
//    volatile boolean running = true;
    boolean running = true;

    void m() {
        System.out.println("m start");
        while (running) {
            // 执行语句的时候 cpu 有可能会空出来一下读一下主内存
            //（不绝对，看 cpu 调度，cpu 有可能会空出来读一下主内存，不可预期行为）
           /* try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }

        System.out.println("m end!");
    }

    public static void main(String[] args) {
        T t = new T();

        new Thread(t::m, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 主线程中进行更改，写入主线程，
        // 没有 volatile t1 依旧在读自己缓冲区的内容
        // 加了 volatile 以后，通知 t1 你的缓冲区的内容过去了，自己去主内存区重新拿
        t.running = false;
    }


}
