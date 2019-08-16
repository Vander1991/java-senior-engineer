package szu.vander.actuator.jmx;

/**
 * @author : Vander
 * @date :   2019/8/12
 * @description :
 */
public interface JmxSimpleMBean {
    String getName();

    void setName(String name);

    String printHello();

    String printHello(String whoName);
}