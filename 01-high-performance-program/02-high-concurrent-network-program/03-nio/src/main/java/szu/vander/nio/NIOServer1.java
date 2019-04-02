package szu.vander.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: caiwj
 * @Date: 2019/4/1
 * @Description:
 */
public class NIOServer1 {

    private static Map<Integer, SocketChannel> socketChannels = new ConcurrentHashMap<>();

    public static void main(String args[]) {

        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false); // 设置为非阻塞模式
            serverSocketChannel.socket().bind(new InetSocketAddress(8080)); // 绑定端口
            System.out.println("服务端启动成功！");
            while(true){
                SocketChannel socketChannel = serverSocketChannel.accept();
                if(socketChannel != null){
                    socketChannel.configureBlocking(false);
                    System.out.println(String.format("接收到来自%s的连接！",socketChannel.getRemoteAddress()));
                    socketChannels.put(socketChannel.hashCode(), socketChannel);
                } else {
                    // 遍历channels，看看是否有数据需要读取
                    for (Map.Entry<Integer, SocketChannel>  readWriteChannelEntry : socketChannels.entrySet()) {
                        ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
                        SocketChannel readWriteChannel = readWriteChannelEntry.getValue();
                        // 表示此时通道没有数据要读的
                        if (socketChannel.read(requestBuffer) == 0){
                            continue;
                        }
                        byte[] content = SocketChannelUtil.readChannelBytes(socketChannel, requestBuffer);
                        System.out.println(String.format("接收到来自%s的消息：%s",readWriteChannel.getRemoteAddress(), new String(content)));
                        SocketChannelUtil.response200(readWriteChannel);
                        socketChannels.remove(readWriteChannel.hashCode());
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
