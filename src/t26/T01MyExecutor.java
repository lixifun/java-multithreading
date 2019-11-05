package t26;

import java.util.concurrent.Executor;

/**
 * 执行器
 */
public class T01MyExecutor implements Executor {

    public static void main(String[] args) {
        new T01MyExecutor().execute(() -> System.out.println("hello executor"));
    }

    @Override
    public void execute(Runnable command) {
        // new Thread(command).start();?
        // new Thread(command).run();
        command.run();
    }
}
