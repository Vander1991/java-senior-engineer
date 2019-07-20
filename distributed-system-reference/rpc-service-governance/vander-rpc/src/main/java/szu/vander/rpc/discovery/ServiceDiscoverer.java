package szu.vander.rpc.discovery;

import java.util.List;

/**
 * @author : Vander
 * @date :   2019/7/14
 * @description :  服务信息发现
 */
public interface ServiceDiscoverer {
	/**
	 * 传入接口名称获取Service列表
	 * @param name
	 * @return
	 */
	List<ServiceInfo> getServiceInfo(String name);
}
