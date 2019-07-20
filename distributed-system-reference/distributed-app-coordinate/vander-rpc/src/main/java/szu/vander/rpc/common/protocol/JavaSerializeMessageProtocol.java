package szu.vander.rpc.common.protocol;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author : caiwj
 * @date :   2019/7/15
 * @description : 使用Java序列化来编组解组
 */
public class JavaSerializeMessageProtocol implements MessageProtocol {

	private byte[] serialize(Object obj) throws Exception {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(bout);
		// 利用Java序列化来writeObj
		out.writeObject(obj);
		return bout.toByteArray();
	}

	@Override
	public byte[] marshallingRequest(Request req) throws Exception {
		return this.serialize(req);
	}

	@Override
	public Request unmarshallingRequest(byte[] data) throws Exception {
		ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));
		return (Request) in.readObject();
	}

	@Override
	public byte[] marshallingResponse(Response rsp) throws Exception {
		return this.serialize(rsp);
	}

	@Override
	public Response unmarshallingResponse(byte[] data) throws Exception {
		ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));
		return (Response) in.readObject();
	}

}
