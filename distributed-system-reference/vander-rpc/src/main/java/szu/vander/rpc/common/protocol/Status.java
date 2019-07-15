package szu.vander.rpc.common.protocol;

/**
 * @author : Vander
 * @date :   2019/7/14
 * @description : 响应编码
 */
public enum Status {
	SUCCESS(200, "SUCCESS"), ERROR(500, "ERROR"), NOT_FOUND(404, "NOT FOUND");

	/**
	 * 响应编码
	 */
	private int code;
	/**
	 * 响应编码描述
	 */
	private String desc;

	private Status(int code, String message) {
		this.code = code;
		this.desc = message;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
