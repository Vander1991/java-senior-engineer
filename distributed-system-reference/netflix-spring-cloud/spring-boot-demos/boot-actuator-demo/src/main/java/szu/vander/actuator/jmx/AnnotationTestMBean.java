package szu.vander.actuator.jmx;

import org.springframework.jmx.export.annotation.*;

/**
 * @author : Vander
 * @date :   2019/8/13
 * @description : 未成功
 */
@ManagedResource(objectName = "bean:name=lionbuleTest", description = "My Managed Bean")
public class AnnotationTestMBean {
    private String name;
    private int age;

    @ManagedAttribute(description = "The Name Attribute")
    public void setName(String name) {
        this.name = name;
    }

    @ManagedAttribute()
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @ManagedOperation(description = "Add two numbers")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "x", description = "The first number"),
            @ManagedOperationParameter(name = "y", description = "The second number")})
    public int add_1(int x, int y) {
        return x + y;
    }

    @ManagedOperation
    public int add_2(int x, int y) {
        return x + y;
    }

    public void dontExposeMe() {
        throw new RuntimeException();
    }
}