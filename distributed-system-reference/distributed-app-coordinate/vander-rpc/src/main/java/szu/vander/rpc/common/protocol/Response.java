package szu.vander.rpc.common.protocol;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class Response implements Serializable {

	private static final long serialVersionUID = -4317845782629589997L;

	private Status status;

	private Map<String, String> headers = new HashMap<>();

	private Object returnValue;

	private Exception exception;

	public Response() {
	}

	public Response(Status status) {
		this.status = status;
	}

	public String getHeader(String name) {
		return this.headers == null ? null : this.headers.get(name);
	}

	public void setHaader(String name, String value) {
		this.headers.put(name, value);
	}
}
