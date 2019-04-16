package szu.vander.test;

/**
 * @Auther: Vander
 * @Date: 2019/4/14
 * @Description:
 */
public class LoaderTestDemo {

    public static String value = initStaticVariable();

    static {
        System.out.println("hello static{}");
    }

    public static String initStaticVariable() {
        System.out.println("init static variable!!!");
        return "i am a static variable!!!";
    }

    public String hello(){
        return "Hello Classloader!!!";
    }

    public static void main(String args[]){

    }

}
