package szu.vander.communicate.variable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VariableCommunicate {

    private static String content = "";

    private static final int runTimes = 10;

    public static void main(String args[]){
        writeTread();
        readTread();
    }

    /**
     * 写线程
     */
    public static void writeTread(){
        new Thread(()->{
            try {
                for(int i =0; i<runTimes; i++){
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                    content = String.format("当前时间：%s", df.format(new Date()));
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ).start();
    }
    /**
     * 读线程
     */
    public static void readTread(){
        new Thread(()->{
            try {
                for(int i =0; i<runTimes; i++){
                    Thread.sleep(1000);
                    System.out.println(content);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
