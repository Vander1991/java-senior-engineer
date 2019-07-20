package szu.vander.rpc.server.register;

import java.io.UnsupportedEncodingException;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

/**
 * @author : Vander
 * @date :   2019/7/14
 * @description :  简易的ZK序列化器，完成字节<->对象间的转换
 */
public class SimpleZkSerializer implements ZkSerializer {

    /**
     * 使用UTF-8进行编码
     */
	private static final String DEFAULT_CHARSET = "UTF-8";

	@Override
	public Object deserialize(byte[] bytes) throws ZkMarshallingError {
		try {
			return new String(bytes, DEFAULT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			throw new ZkMarshallingError(e);
		}
	}

	@Override
	public byte[] serialize(Object obj) throws ZkMarshallingError {
		try {
			return String.valueOf(obj).getBytes(DEFAULT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			throw new ZkMarshallingError(e);
		}
	}
}
