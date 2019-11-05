package t23;

/**
 * 既不用加锁，也可以实现懒加载
 */
public class Singleton {
    private Singleton() {
        System.out.println("single");
    }

    private static class Inner {
        private static Singleton s= new Singleton();
    }

    public static Singleton getInstance() {
        return Inner.s;
    }

    public static void main(String[] args) {

    }
}
