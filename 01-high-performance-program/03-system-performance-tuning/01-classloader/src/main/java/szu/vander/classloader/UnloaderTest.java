package szu.vander.classloader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * 指定class 进行卸载
 */
public class UnloaderTest {
    public static void main(String[] args) throws Exception {
        URL classUrl = new URL("file:C:\\idea_workspace\\java-senior-engineer\\01-high-performance-program\\03-system-performance-tuning\\01-classloader\\target\\test-classes\\");//jvm 类放在位置
        // 创建一个新的类加载器
        URLClassLoader loader = new URLClassLoader(new URL[]{classUrl});
        while (true) {
            if(loader == null){
                break;
            }
            // 问题：静态块触发
            Class clazz = loader.loadClass("szu.vander.test.LoaderTestDemo");
            System.out.println("LoaderTestDemo所使用的类加载器：" + clazz.getClassLoader());

            Object newInstance = clazz.newInstance();
            Object value = clazz.getMethod("hello").invoke(newInstance);
            System.out.println("调用hello方法获得的返回值为：" + value);

            Thread.sleep(3000L); // 1秒执行一次
            System.out.println();

            //  help gc  -verbose:class
            newInstance = null;
            loader = null;

        }
        System.gc();
        Thread.sleep(10000);
    }
}

