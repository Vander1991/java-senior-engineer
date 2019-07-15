package szu.vander.rpc.common.protocol;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : Vander
 * @date :   2019/7/14
 * @description :
 */
@Setter
@Getter
public class Request implements Serializable {

	private static final long serialVersionUID = -5200571424236772650L;

	private String serviceName;

	private String method;

	private Map<String, String> headers = new HashMap<>();

	private Class<?>[] prameterTypes;

	private Object[] parameters;

	public String getHeaders(String name) {
		return this.headers == null ? null : this.headers.get(name);
	}

	public void setHaader(String name, String value) {
		this.headers.put(name, value);
	}

	public Object[] getParameters() {
		return this.parameters;
	}

}
