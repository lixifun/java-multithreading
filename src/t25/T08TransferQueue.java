package t25;

import java.util.concurrent.LinkedTransferQueue;

public class T08TransferQueue {
    public static void main(String[] args) throws InterruptedException {

        LinkedTransferQueue<String> strs = new LinkedTransferQueue<>();

        /*new Thread(() -> {
            try {
                System.out.println(strs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();*/

        // 跟高的并发的情况下
//        strs.transfer("aaa"); // 没有消费者，则阻塞

        strs.put("aaa");
        new Thread(() -> {
            try {
                System.out.println(strs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
