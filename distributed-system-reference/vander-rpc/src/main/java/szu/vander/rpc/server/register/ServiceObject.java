package szu.vander.rpc.server.register;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ServiceObject {

	/**
	 * 服务名
	 */
	private String name;

	/**
	 * 服务对象接口
	 */
	private Class<?> serviceInterface;

	/**
	 * 服务对象
	 */
	private Object serviceObj;

}
