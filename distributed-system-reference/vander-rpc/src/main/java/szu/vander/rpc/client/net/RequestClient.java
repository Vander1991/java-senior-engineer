package szu.vander.rpc.client.net;


import szu.vander.rpc.discovery.ServiceInfo;

/**
 * @author : Vander
 * @date :   2019/7/14
 * @description :  请求客户端接口，用于将请求发送到Service并接收响应
 */
public interface RequestClient {
	/**
	 * 发送请求并接收响应
	 * @param data
	 * @param serviceInfo
	 * @return
	 * @throws Throwable
	 */
	byte[] sendRequest(byte[] data, ServiceInfo serviceInfo) throws Throwable;
}
