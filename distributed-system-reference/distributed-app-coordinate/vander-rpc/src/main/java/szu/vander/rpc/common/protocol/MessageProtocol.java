package szu.vander.rpc.common.protocol;


/**
 * @author : Vander
 * @date :   2019/7/14
 * @description :  消息协议接口
 */
public interface MessageProtocol {

	/**
	 * 编组请求为字节
	 * @param req
	 * @return
	 * @throws Exception
	 */
	byte[] marshallingRequest(Request req) throws Exception;

	/**
	 * 解组字节为请求
	 * @param data
	 * @return
	 * @throws Exception
	 */
	Request unmarshallingRequest(byte[] data) throws Exception;

	/**
	 * 编组响应为字节
	 * @param rsp
	 * @return
	 * @throws Exception
	 */
	byte[] marshallingResponse(Response rsp) throws Exception;

	/**
	 * 解组字节为响应
	 * @param data
	 * @return
	 * @throws Exception
	 */
	Response unmarshallingResponse(byte[] data) throws Exception;
}
