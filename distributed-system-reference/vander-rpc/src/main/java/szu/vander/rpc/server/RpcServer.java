package szu.vander.rpc.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : caiwj
 * @date :   2019/7/15
 * @description : 
 */
@Setter
@Getter
@AllArgsConstructor
public abstract class RpcServer {

	protected int port;

	protected String protocol;

	protected RequestHandler handler;

	/**
	 * 开启服务
	 */
	public abstract void start();

	/**
	 * 停止服务
	 */
	public abstract void stop();

}
