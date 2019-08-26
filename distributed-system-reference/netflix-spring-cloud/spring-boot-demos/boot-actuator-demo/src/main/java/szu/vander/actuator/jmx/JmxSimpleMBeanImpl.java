package szu.vander.actuator.jmx;

import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * @author : Vander
 * @date :   2019/8/12
 * @description :
 */
@ManagedResource
public class JmxSimpleMBeanImpl implements JmxSimpleMBean {
    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String printHello() {
        return "JmxSimpleMBeanImpl " + name;
    }

    @Override
    public String printHello(String whoName) {
        return "JmxSimpleMBeanImpl  " + whoName;
    }
}