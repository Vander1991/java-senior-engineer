package szu.vander.rpc.server.register;

/**
 * @author : caiwj
 * @date :   2019/7/15
 * @description : 服务注册接口
 */
public interface ServiceRegister {
	/**
	 * 服务注册，需要提供服务对象的相关信息，协议，服务的端口,服务的IP直接获取本机IP
	 * @param so
	 * @param protocol
	 * @param port
	 * @throws Exception
	 */
	void register(ServiceObject so, String protocol, int port) throws Exception;

	/**
	 * 通过接口名称来获取服务对象
	 * @param name
	 * @return
	 * @throws Exception
	 */
	ServiceObject getServiceObject(String name) throws Exception;
}
