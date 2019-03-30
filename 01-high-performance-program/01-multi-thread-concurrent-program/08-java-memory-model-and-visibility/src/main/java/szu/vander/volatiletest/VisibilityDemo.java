package szu.vander.volatiletest;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: Vander
 * @Date: 2019/3/26
 * @Description:
 */
public class VisibilityDemo {

    private volatile boolean flag = true;

    public static void main(String args[]) throws InterruptedException {
        VisibilityDemo demo = new VisibilityDemo();
        Thread thread = new Thread(() -> {
            int i = 0;
            while(demo.flag){
                i++;
            }
            System.out.println(i);
        }
        );
        thread.start();

        TimeUnit.SECONDS.sleep(2);
        demo.flag = false;
        System.out.println("flag被设置为" + demo.flag);
    }
}
