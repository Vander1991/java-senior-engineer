package szu.vander.thread;

/**
 * 不管使用Stop来终止线程还是用Interrupt来终止，finally块都会运行
 */
public class StopThread extends Thread {
    private int i = 0, j = 0;

    @Override
    public void run() {
        synchronized (this) {
            // 增加同步锁，确保线程安全
            ++i;
            try {
                // 休眠10秒,模拟耗时操作
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                ++j;
            }
        }
    }

    public void print() {
        System.out.println("i=" + i + " j=" + j);
    }

}