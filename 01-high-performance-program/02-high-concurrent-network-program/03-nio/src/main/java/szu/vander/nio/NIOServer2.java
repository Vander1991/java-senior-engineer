package szu.vander.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.spi.SelectorProvider;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: caiwj
 * @Date: 2019/4/1
 * @Description:
 */
public class NIOServer2 {

    private static Map<Integer, SocketChannel> socketChannels = new ConcurrentHashMap<>();

    public static void main(String args[]) {

        try {
            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false); // 设置为非阻塞模式
            serverSocketChannel.socket().bind(new InetSocketAddress(8080)); // 绑定端口
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务端启动成功！");
            while (true) {
                for (SelectionKey selectionKey : selector.selectedKeys()) {
                    System.out.println("selectKey");
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel channel = (ServerSocketChannel) selectionKey.attachment();
                        SocketChannel socketChannel = channel.accept();
                        System.out.println(String.format("接收到来自%s的连接！", socketChannel.getRemoteAddress()));
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }

                    if (selectionKey.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) selectionKey.attachment();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        SocketChannelUtil.readChannelBytes(socketChannel, byteBuffer);
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
