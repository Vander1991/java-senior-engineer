package szu.vander.threadpool.scheduledthreadpoolexector;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: Vander
 * @Date: 2019/3/26
 * @Description:
 */
public class FixedRateDemo {

    public static void main(String args[]){
        testFixedRate();
    }

    public static void testFixedRate(){
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(5);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 0, 2, TimeUnit.SECONDS);
    }

}
