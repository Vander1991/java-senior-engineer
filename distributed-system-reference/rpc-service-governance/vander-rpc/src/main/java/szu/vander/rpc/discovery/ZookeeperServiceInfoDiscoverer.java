package szu.vander.rpc.discovery;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.I0Itec.zkclient.ZkClient;

import com.alibaba.fastjson.JSON;
import szu.vander.rpc.common.constant.RpcReferConstant;
import szu.vander.rpc.server.register.SimpleZkSerializer;
import szu.vander.rpc.util.PropertiesUtils;

/**
 * @author : Vander
 * @date :   2019/7/14
 * @description :  通过读取ZK上特定节点获取服务信息
 */
public class ZookeeperServiceInfoDiscoverer implements ServiceDiscoverer {

	private ZkClient zkClient;

	public ZookeeperServiceInfoDiscoverer() {
		String addr = PropertiesUtils.getProperties(RpcReferConstant.PROPERTIES_ZK_SERVER_KEY);
		zkClient = new ZkClient(addr);
		zkClient.setZkSerializer(new SimpleZkSerializer());
	}

	@Override
	public List<ServiceInfo> getServiceInfo(String name) {
		String servicePath = RpcReferConstant.ZK_SERVICE_ROOT_PATH + "/" + name + "/service";
		List<String> children = zkClient.getChildren(servicePath);
		List<ServiceInfo> resources = new ArrayList<>();
		for (String serviceInfoStr : children) {
			try {
				String deCh = URLDecoder.decode(serviceInfoStr, "UTF-8");
				ServiceInfo r = JSON.parseObject(deCh, ServiceInfo.class);
				resources.add(r);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return resources;
	}

}
