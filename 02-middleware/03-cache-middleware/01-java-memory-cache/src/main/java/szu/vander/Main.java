package szu.vander;

import szu.vander.cache.MapCacheDemo;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: caiwj
 * @Date: 2019/5/13
 * @Description:
 */
public class Main {

    public static void main(String args[]) throws InterruptedException {
        MapCacheDemo mapCacheDemo = new MapCacheDemo();
        mapCacheDemo.add("001", "Vander", 1000);
        mapCacheDemo.add("002", "Panda", 2000);
        System.out.println("当前的缓存大小：" + mapCacheDemo.size());
        TimeUnit.SECONDS.sleep(1);
        System.out.println("当前的缓存大小：" + mapCacheDemo.size());
    }

}
