package szu.vander.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NIOClient {

    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));
        while (!socketChannel.finishConnect()) {
            // 没连接上,则一直等待
            Thread.yield();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入：");
        // 发送内容
        String msg = scanner.nextLine();
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
        // 读取响应
        System.out.println("收到服务端响应:");
        ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
        byte[] content = SocketChannelUtil.readChannelBytes(socketChannel, requestBuffer);
        System.out.println(new String(content));

        scanner.close();
        socketChannel.close();
    }

}
