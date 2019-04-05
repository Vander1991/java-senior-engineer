package szu.vander.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: caiwj
 * @Date: 2019/4/1
 * @Description: 此处有一点很奇怪，就是若客户端写完数据给服务端，然后客户端关闭了，服务端的Selector会一直监听到有数据需要读取
 */
public class NIOServer2 {

    public static void main(String args[]) {

        try {
            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false); // 设置为非阻塞模式
            serverSocketChannel.socket().bind(new InetSocketAddress(8080)); // 绑定端口
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, serverSocketChannel);
            System.out.println("服务端启动成功！");
            while (true) {
                selector.select();
                System.out.println("选择器发现有事件要处理！");
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey selectionKey : selectionKeys) {
                    try {
                        if (selectionKey.isAcceptable()) {
                            ServerSocketChannel channel = (ServerSocketChannel) selectionKey.attachment();
                            SocketChannel socketChannel = channel.accept();
                            System.out.println(String.format("接收到来自%s的连接！", socketChannel.getRemoteAddress()));
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ, socketChannel);
                        }

                        if (selectionKey.isReadable()) {
                            SocketChannel socketChannel = (SocketChannel) selectionKey.attachment();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            byte[] content = SocketChannelUtil.readChannelBytes(socketChannel, byteBuffer);
                            if (content == null) {
                                // 说明没有数据传过来
                                System.out.println("选择器虽然发现有读取事件要处理，但是却读取不到数据，说明连接很可能已经关闭！");
                                selectionKey.cancel();
                                continue;
                            }
                            System.out.println(String.format("接收到来自%s的数据：%s", socketChannel.getRemoteAddress(), new String(content)));
                            SocketChannelUtil.response200(socketChannel);
                        }
                    } catch (IOException e) {
                        selectionKey.cancel();
                        e.printStackTrace();
                    }
                }
                // 清理掉当前的SelectionKeys
                selectionKeys.clear();
                // 处理完事件再非阻塞的选择一次
                selector.selectNow();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
