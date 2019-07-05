package szu.vander.demo.watch;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.io.IOException;

/**
 * @author : Vander
 * @date :   2019/7/5
 * @description : 
 */
public class ZkClientWatchDemo {

	public static void main(String[] args) {
		// 创建一个zk客户端
		ZkClient client = new ZkClient("192.168.159.88:2181");

		client.setZkSerializer(new MyZkSerializer());

		client.subscribeDataChanges("/watch_data", new IZkDataListener() {

			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				System.out.println("----收到节点被删除了-------------");
			}

			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				System.out.println("----收到节点数据变化：" + data + "-------------");
			}
		});
		// 防止程序终止
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
