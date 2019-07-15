package szu.vander.rpc.discovery;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : Vander
 * @date :   2019/7/14
 * @description :  服务信息
 */
@Setter
@Getter
public class ServiceInfo {

	private String name;

	private String protocol;

	private String address;

}
