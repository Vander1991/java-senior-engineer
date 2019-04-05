package szu.vander.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Auther: caiwj
 * @Date: 2019/4/1
 * @Description:
 */
public class SocketChannelUtil {

    public static void response200(SocketChannel socketChannel) throws IOException {
        // 响应结果 200
        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: 11\r\n\r\n" +
                "Hello World";
        ByteBuffer buffer = ByteBuffer.wrap(response.getBytes());
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);// 非阻塞
        }
    }

    public static byte[] readChannelBytes(SocketChannel socketChannel, ByteBuffer byteBuffer) throws IOException {
        while(socketChannel.isOpen() && socketChannel.read(byteBuffer) != -1){
            if (byteBuffer.position() > 0) break;
        }
        if (byteBuffer.position() == 0) {
            // 说明缓冲区没有数据
            return null;
        }
        byteBuffer.flip();
        byte[] content = new byte[byteBuffer.limit()];
        byteBuffer.get(content);
        return content;
    }

}
