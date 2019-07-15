package szu.vander.rpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;
import szu.vander.rpc.client.net.RequestClient;
import szu.vander.rpc.common.protocol.MessageProtocol;
import szu.vander.rpc.common.protocol.Request;
import szu.vander.rpc.common.protocol.Response;
import szu.vander.rpc.discovery.ServiceInfo;
import szu.vander.rpc.discovery.ServiceDiscoverer;

/**
 * @author : Vander
 * @date :   2019/7/14
 * @description :  ClientStub完成请求的编组，响应的解组
 */
@Setter
@Getter
public class ClientStubProxyFactory {

	private ServiceDiscoverer serviceDiscoverer;

	private Map<String, MessageProtocol> supportMessageProtocols;

	private RequestClient requestClient;

	private Map<Class<?>, Object> objectCache = new HashMap<>();

	private static final String TO_STRING_METHOD = "toString";

	private static final String HASH_CODE_METHOD = "hashCode";

	/**
	 * 使用JDK动态代理生成继承接口的实例，调用接口方法时会被InvocationHandler拦截
	 * @param serviceInterface
	 * @param <T>
	 * @return
	 */
	public <T> T getProxy(Class<T> serviceInterface) {
		T obj = (T) this.objectCache.get(serviceInterface);
		if (obj == null) {
			obj = (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class<?>[] { serviceInterface },
					new ClientStubInvocationHandler(serviceInterface));
			this.objectCache.put(serviceInterface, obj);
		}
		return obj;
	}

	private class ClientStubInvocationHandler implements InvocationHandler {
		private Class<?> serviceInterface;

		private Random random = new Random();

		public ClientStubInvocationHandler(Class<?> serviceInterface) {
			super();
			this.serviceInterface = serviceInterface;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

			if (TO_STRING_METHOD.equals(method.getName())) {
				return proxy.getClass().toString();
			}

			if (HASH_CODE_METHOD.equals(method.getName())) {
				return 0;
			}

			// 1、获得服务信息
			String serviceName = this.serviceInterface.getName();
			List<ServiceInfo> serviceInfos = serviceDiscoverer.getServiceInfo(serviceName);

			if (serviceInfos == null || serviceInfos.size() == 0) {
				throw new Exception("远程服务不存在！");
			}

			// 随机选择一个服务提供者（软负载均衡）
			ServiceInfo serviceInfo = serviceInfos.get(random.nextInt(serviceInfos.size()));

			// 2、构造request对象
			Request req = new Request();
			req.setServiceName(serviceInfo.getName());
			req.setMethod(method.getName());
			req.setPrameterTypes(method.getParameterTypes());
			req.setParameters(args);

			// 3、协议层编组
			// 获得该方法对应的协议
			MessageProtocol protocol = supportMessageProtocols.get(serviceInfo.getProtocol());
			// 编组请求
			byte[] data = protocol.marshallingRequest(req);

			// 4、调用网络层发送请求
			byte[] repData = requestClient.sendRequest(data, serviceInfo);

			// 5、解组响应消息
			Response rsp = protocol.unmarshallingResponse(repData);

			// 6、结果处理
			if (rsp.getException() != null) {
				throw rsp.getException();
			}

			return rsp.getReturnValue();
		}
	}
}
