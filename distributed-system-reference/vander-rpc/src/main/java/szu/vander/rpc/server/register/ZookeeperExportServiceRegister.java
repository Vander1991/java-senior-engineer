package szu.vander.rpc.server.register;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;

import org.I0Itec.zkclient.ZkClient;

import com.alibaba.fastjson.JSON;
import szu.vander.rpc.common.constant.RpcReferConstant;
import szu.vander.rpc.discovery.ServiceInfo;
import szu.vander.rpc.util.PropertiesUtils;

/**
 * @author : caiwj
 * @date :   2019/7/15
 * @description : Zookeeper方式获取远程服务信息类。
 */
public class ZookeeperExportServiceRegister extends DefaultServiceRegister implements ServiceRegister {

	private ZkClient client;

	public ZookeeperExportServiceRegister() {
		String addr = PropertiesUtils.getProperties(RpcReferConstant.PROPERTIES_ZK_SERVER_KEY);
		client = new ZkClient(addr);
		client.setZkSerializer(new SimpleZkSerializer());
	}

	@Override
	public void register(ServiceObject so, String protocolName, int port) throws Exception {
		super.register(so, protocolName, port);
		ServiceInfo serviceInfo = new ServiceInfo();

		String host = InetAddress.getLocalHost().getHostAddress();
		String address = host + ":" + port;
		serviceInfo.setAddress(address);
		serviceInfo.setName(so.getServiceInterface().getName());
		serviceInfo.setProtocol(protocolName);
		this.exportService(serviceInfo);

	}

	/**
	 * 暴露服务-即将服务信息存到ZK的节点，客户端访问ZK的节点获取服务信息
	 * @param serviceResource
	 */
	private void exportService(ServiceInfo serviceResource) {
		String serviceName = serviceResource.getName();
		String uri = JSON.toJSONString(serviceResource);
		try {
			uri = URLEncoder.encode(uri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String servicePath = RpcReferConstant.ZK_SERVICE_ROOT_PATH + "/" + serviceName + "/service";
		if (!client.exists(servicePath)) {
			client.createPersistent(servicePath, true);
		}
		String uriPath = servicePath + "/" + uri;
		if (client.exists(uriPath)) {
			client.delete(uriPath);
		}
		client.createEphemeral(uriPath);
	}
}
