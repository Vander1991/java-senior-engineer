package szu.vander.service;

/**
 * @author : Vander
 * @date :   2019/8/3
 * @description :  
 */
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}