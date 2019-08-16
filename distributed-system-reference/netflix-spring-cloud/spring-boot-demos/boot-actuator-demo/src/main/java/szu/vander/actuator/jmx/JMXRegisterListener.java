package szu.vander.actuator.jmx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author : Vander
 * @date :   2019/8/12
 * @description : 注册自己定义的MBean
 */
public class JMXRegisterListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    JmxSimpleMBeanImpl jmxTest;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            //create mbean server
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            //create object name
            ObjectName objectName = null;
            objectName = new ObjectName("jmxBean:name=testJMX");
            // 注册
            server.registerMBean(jmxTest, objectName);
            /**
             * JMXConnectorServer service
             */
            //这句话非常重要，不能缺少！注册一个端口，绑定url后，客户端就可以使用rmi通过url方式来连接JMXConnectorServer
            Registry registry = LocateRegistry.createRegistry(1099);

            //构造JMXServiceURL
            JMXServiceURL jmxServiceURL = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi");
            //创建JMXConnectorServer
            JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(jmxServiceURL, null, server);
            //启动
            cs.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
