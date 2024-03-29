package szu.vander.mq.discovery;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

// 组播的形式自动发现服务器: http://activemq.apache.org/multicast-transport-reference.html
// 自己电脑上启动一个activemq，在activemq.xml connector加上
// <transportConnector name="openwire" uri="tcp://0.0.0.0:61616?trace=true&amp;maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600" discoveryUri="multicast://default"/>
// 玩一玩就行，跨网络啥的，要配置网络.客户端不用这个，一般是服务器集群用得到
public class ConsumerAndProducerMulticastDiscovery {
    public static void main(String[] args) {
        ActiveMQConnectionFactory connectionFactory = null;
        Connection conn = null;
        Session session = null;
        MessageConsumer consumer = null;

        try {
            // 1、创建连接工厂（不需要手动指定，自动发现）
            connectionFactory = new ActiveMQConnectionFactory("discovery:(multicast://default)");
            // 2、创建连接对象
            conn = connectionFactory.createConnection();
            conn.start();
            // 3、 创建session
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // 4、创建点对点接收的目标
            Destination destination = session.createQueue("queue1");
            // 5、创建生产者消息
            MessageProducer producer = session.createProducer(destination);
            // 设置生产者的模式，有两种可选
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            // 6、创建一条消息
            String text = "Hello world!";
            TextMessage message = session.createTextMessage(text);
            // 7、发送消息
            producer.send(message);
            // 8、创建消费者消息
            consumer = session.createConsumer(destination);
            // 9、接收消息
            Message consumerMessage = consumer.receive();
            if (consumerMessage instanceof TextMessage) {
                System.out.println("收到文本消息：" + ((TextMessage) consumerMessage).getText());
            } else {
                System.out.println(consumerMessage);
            }

            consumer.close();
            session.close();
            conn.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
