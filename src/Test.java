import java.util.concurrent.Callable;

public class Test {
    public static void main(String[] args) {
        System.out.println("主线程正在运行");
        Object a =new Object();
        Object b =new Object();
        Object c =new Object();
        Thread th1 = new Thread(new Thread1("A",c,a));
        Thread th2 =  new Thread(new Thread1("B",a,b));
        Thread th3 =  new Thread(new Thread1("C",b,c));
        th1.start();
        th2.start();
        th3.start();
        System.out.println("主线程运行结束");
    }

    static class Thread1 implements Runnable{

        private String name;
        private Object prev;
        private Object self;
        static int count = 1000;
        public Thread1(String name,Object prev,Object self) {
            this.name = name;
            this.prev = prev;
            this.self = self;
        }

        @Override
        public void run() {
            while (count>0){
                synchronized (prev){
                    synchronized (self){
                        count--;
                        System.out.println(name +"以拿取小球，还剩"+count);
                        self.notify();
                    }
                    try {
                        prev.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}


