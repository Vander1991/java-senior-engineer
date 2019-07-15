package szu.vander.rpc.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import szu.vander.rpc.common.protocol.MessageProtocol;
import szu.vander.rpc.common.protocol.Request;
import szu.vander.rpc.common.protocol.Response;
import szu.vander.rpc.common.protocol.Status;
import szu.vander.rpc.server.register.ServiceObject;
import szu.vander.rpc.server.register.ServiceRegister;

/**
 * @author : caiwj
 * @date :   2019/7/15
 * @description : 
 */
@Getter
@Setter
@AllArgsConstructor
public class RequestHandler {

	private MessageProtocol messageProtocol;

	private ServiceRegister serviceRegister;

	public byte[] handleRequest(byte[] data) throws Exception {
		// 1、解组消息
		Request req = this.messageProtocol.unmarshallingRequest(data);

		// 2、查找服务对象
		ServiceObject so = this.serviceRegister.getServiceObject(req.getServiceName());

		Response rsp = null;

		if (so == null) {
			rsp = new Response(Status.NOT_FOUND);
		} else {
			// 3、反射调用对应的过程方法
			try {
				Method m = so.getServiceInterface().getMethod(req.getMethod(), req.getPrameterTypes());
				Object returnValue = m.invoke(so.getServiceObj(), req.getParameters());
				rsp = new Response(Status.SUCCESS);
				rsp.setReturnValue(returnValue);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				rsp = new Response(Status.ERROR);
				rsp.setException(e);
			}
		}

		// 4、编组响应消息
		return this.messageProtocol.marshallingResponse(rsp);
	}

}
