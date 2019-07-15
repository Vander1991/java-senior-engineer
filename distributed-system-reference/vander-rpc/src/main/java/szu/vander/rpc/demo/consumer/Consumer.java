package szu.vander.rpc.demo.consumer;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import szu.vander.rpc.client.ClientStubProxyFactory;
import szu.vander.rpc.client.net.NettyClient;
import szu.vander.rpc.common.protocol.JavaSerializeMessageProtocol;
import szu.vander.rpc.common.protocol.MessageProtocol;
import szu.vander.rpc.demo.DemoService;
import szu.vander.rpc.discovery.ZookeeperServiceInfoDiscoverer;

public class Consumer {
	public static void main(String[] args) throws Exception {

		ClientStubProxyFactory cspf = new ClientStubProxyFactory();
		// 设置服务发现者
		cspf.setServiceDiscoverer(new ZookeeperServiceInfoDiscoverer());

		// 设置支持的协议
		Map<String, MessageProtocol> supportMessageProtocols = new HashMap<>();
		supportMessageProtocols.put("javas", new JavaSerializeMessageProtocol());
		cspf.setSupportMessageProtocols(supportMessageProtocols);

		// 设置网络层实现
		cspf.setRequestClient(new NettyClient());

		DemoService demoService = cspf.getProxy(DemoService.class); // 获取远程服务代理
		String hello = demoService.sayHello("world"); // 执行远程方法
		System.out.println(hello); // 显示调用结果

		System.out.println(demoService.multiPoint(new Point(5, 10), 2));
	}
}
