package szu.vander.demo.elect;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import szu.vander.demo.watch.MyZkSerializer;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author : Vander
 * @date :   2019/7/5
 * @description :
 */
public class MasterElectionDemo {

    private final static String serverString = "192.168.159.88:2181";

    static class Server {

        private String cluster, name, address;

        private final String path, value;

        private String master;

        public Server(String cluster, String name, String address) {
            super();
            this.cluster = cluster;
            this.name = name;
            this.address = address;
            path = "/" + this.cluster + "Master";
            value = "name:" + name + " address:" + address;

            final ZkClient client = new ZkClient(serverString);
            client.setZkSerializer(new MyZkSerializer());

            ThreadPoolExecutor threadPoolExecutor;

            // 注意，此处通过自定义线程池创建后台线程，这是因为节点的选举应该是以后台线程的方式一直在跑的
            try {
                threadPoolExecutor = new UserDefinedThreadPool("MasterElectionPool").threadPoolExecutor();
                threadPoolExecutor.submit(() -> {
                    electionMaster(client);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void electionMaster(ZkClient client) {
            // 各个节点都往/*Master写入值，写成功的就是Master
            try {
                client.createEphemeral(path, value);
                master = client.readData(path);
                System.out.println(value + "创建节点成功，成为Master");
            } catch (ZkNodeExistsException e) {
                // 写不成功的，就读取到Master是谁
                master = client.readData(path);
                System.out.println("Master为：" + master);
            }

            final CountDownLatch cdl = new CountDownLatch(1);

            // 注册watcher
            IZkDataListener listener = new IZkDataListener() {
                @Override
                public void handleDataDeleted(String dataPath) throws Exception {
                    System.out.println("-----监听到节点被删除");
                    cdl.countDown();
                }

                @Override
                public void handleDataChange(String dataPath, Object data) throws Exception {
                    System.out.println("-----监听到节点被修改");
                }
            };

            // 注册/*Master的数据变化事件，一旦监听到该数据被删除，则会开始countDown
            // CountDown到0后会将await的线程唤醒。
            client.subscribeDataChanges(path, listener);

            // 让自己阻塞
            if (client.exists(path)) {
                try {
                    cdl.await();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            // 醒来后，取消watcher
            client.unsubscribeDataChanges(path, listener);
            // 递归调自己（下一次选举）
            electionMaster(client);

        }

    }

    public static void main(String[] args) {
        // 测试时，依次开启多个Server实例java进程，然后停止获取的master的节点，看谁抢到Master
//        Server s1 = new Server("cluster1", "server1", "192.168.1.1:8081");
//        Server s2 = new Server("cluster1", "server2", "192.168.1.1:8082");
//        Server s3 = new Server("cluster1", "server3", "192.168.1.1:8083");
        Server s4 = new Server("cluster1", "server4", "192.168.1.1:8084");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
