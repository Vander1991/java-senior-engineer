package szu.vander.communicate.file;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class FileCommunicate {

    public static void main(String args[]) {
        createWriteThread();
        createReadThread();
    }

    /**
     * 创建写文件线程
     */
    public static void createWriteThread() {
        new Thread(() -> {
            int runTime = 10;
            while(runTime-- > 0){
                File file = new File("01-high-performance-program/05-communication-of-thread/src/main/resources/fileCommunicate.log");
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String now = String.format("当前时间：%s", df.format(new Date()));
                try {
                    FileUtils.writeStringToFile(file, now, "UTF-8");
                    Thread.sleep(1000);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 创建读文件线程
     */
    public static void createReadThread() {
        new Thread(() -> {
            int runTime = 10;
            while(runTime-- > 0){
                File file = new File("01-high-performance-program/05-communication-of-thread/src/main/resources/fileCommunicate.log");
                String now = String.format("当前时间：%s", String.valueOf(System.currentTimeMillis()));
                try {
                    String content = null;
                    while((content = FileUtils.readFileToString(file)) != null){
                        System.out.println(content);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}


