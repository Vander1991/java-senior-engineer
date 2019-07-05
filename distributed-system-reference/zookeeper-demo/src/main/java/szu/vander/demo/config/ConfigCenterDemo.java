package szu.vander.demo.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;
import szu.vander.demo.watch.MyZkSerializer;

/**
 * @author : Vander
 * @date :   2019/7/5
 * @description : 配置中心示例
 */
public class ConfigCenterDemo {

	private static String serverString = "192.168.159.88:2181";

    /**
     * 将单个配置放到zookeeper上
     */
	public void put2Zk() {
		ZkClient client = new ZkClient(serverString);
		client.setZkSerializer(new MyZkSerializer());
		String configPath = "/db.address";
		String value = "192.168.125.240";
		if (client.exists(configPath)) {
			client.writeData(configPath, value);
		} else {
			client.createPersistent(configPath, value);
		}
		client.close();
	}

    /**
     * 将配置文件的内容存放到zk节点上
     * @throws IOException
     */
	public void putConfigFile2ZK() throws IOException {

		File f = new File(this.getClass().getResource("/config.properties").getFile());
		FileInputStream fin = new FileInputStream(f);
		byte[] datas = new byte[(int) f.length()];
		fin.read(datas);
		fin.close();

		ZkClient client = new ZkClient(serverString);
		client.setZkSerializer(new BytesPushThroughSerializer());
		String configPath = "/configFile";
		if (client.exists(configPath)) {
			client.writeData(configPath, datas);
		} else {
			client.createPersistent(configPath, datas);
		}
		client.close();
	}

    /**
     * 需要配置的服务都从zk上取，并注册watch来实时获得配置更新
     */
	public void getConfigFromZk() {
		ZkClient client = new ZkClient(serverString);
		client.setZkSerializer(new MyZkSerializer());
		String configPath = "/db.address";
		String value = client.readData(configPath);
		System.out.println("从zk读到配置config1的值为：" + value);
		// 监控配置的更新
		client.subscribeDataChanges(configPath, new IZkDataListener() {

			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				// TODO Auto-generated method stub
			}

			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				System.out.println("获得更新的配置值：" + data);
			}
		});

		// 这里只是为演示实时获取到配置值更新而加的等待。实际项目应用中根据具体场景写（可用阻塞方式）
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		ConfigCenterDemo demo = new ConfigCenterDemo();
		demo.put2Zk();
		demo.putConfigFile2ZK();

		demo.getConfigFromZk();
	}

}
