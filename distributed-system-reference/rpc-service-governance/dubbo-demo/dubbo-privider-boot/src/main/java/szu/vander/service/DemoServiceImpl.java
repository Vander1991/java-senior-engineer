package szu.vander.service;


import com.alibaba.dubbo.config.annotation.Service;

/**
 * @author : Vander
 * @date :   2019/8/3
 * @description :  
 */
@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}