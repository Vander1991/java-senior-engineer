package szu.vander.rpc.demo.provider;


import szu.vander.rpc.common.constant.RpcReferConstant;
import szu.vander.rpc.common.protocol.JavaSerializeMessageProtocol;
import szu.vander.rpc.demo.DemoService;
import szu.vander.rpc.server.NettyRpcServer;
import szu.vander.rpc.server.RequestHandler;
import szu.vander.rpc.server.RpcServer;
import szu.vander.rpc.server.register.ServiceObject;
import szu.vander.rpc.server.register.ServiceRegister;
import szu.vander.rpc.server.register.ZookeeperExportServiceRegister;
import szu.vander.rpc.util.PropertiesUtils;

/**
 * @author : caiwj
 * @date :   2019/7/15
 * @description : 服务提供者
 */
public class Provider {


	/**
	 * 服务注册
	 * @return
	 */
	private static ServiceRegister getServiceRegister(int servicePort, String messageProtocol) throws Exception {
		ServiceRegister sr = new ZookeeperExportServiceRegister();
		DemoService ds = new DemoServiceImpl();
		ServiceObject so = new ServiceObject(DemoService.class.getName(), DemoService.class, ds);
		sr.register(so, messageProtocol, servicePort);
		return sr;
	}

	public static void main(String[] args) throws Exception {

		int port = Integer.parseInt(PropertiesUtils.getProperties(RpcReferConstant.PROPERTIES_RPC_SERVER_KEY));
		String protocol = PropertiesUtils.getProperties(RpcReferConstant.PROPERTIES_RPC_PROTOCOL_KEY);

		ServiceRegister serviceRegister = getServiceRegister(port, protocol);

		RequestHandler reqHandler = new RequestHandler(new JavaSerializeMessageProtocol(), serviceRegister);
		// 启动Netty RPC Server
		RpcServer server = new NettyRpcServer(port, protocol, reqHandler);
		server.start();

		System.in.read(); // 按任意键退出
		server.stop();
	}
}
