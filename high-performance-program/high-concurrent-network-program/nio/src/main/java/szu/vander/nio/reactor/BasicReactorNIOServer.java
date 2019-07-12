package szu.vander.nio.reactor;

import szu.vander.nio.SocketChannelUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * @Auther: Vander
 * @Date: 2019/4/5
 * @Description:
 */
public class BasicReactorNIOServer {

    public BasicReactorNIOServer(int port) throws IOException {
        Reactor reactor = new Reactor(port);
        reactor.run();
    }

    class Reactor implements Runnable {
        final Selector selector;
        final ServerSocketChannel serverSocket;

        /*
            Alternatively, use explicit SPI provider:
            SelectorProvider p = SelectorProvider.provider();
            selector = p.openSelector();
            serverSocket = p.openServerSocketChannel();
        */
        Reactor(int port) throws IOException {
            selector = Selector.open();
            serverSocket = ServerSocketChannel.open();
            serverSocket.socket().bind(new InetSocketAddress(port));
            serverSocket.configureBlocking(false);
            SelectionKey selectionKey = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            selectionKey.attach(new Acceptor());
        }

        public void run() { // normally in a new Thread
            try {
                while (!Thread.interrupted()) {
                    selector.select();
                    Set<SelectionKey> selected = selector.selectedKeys();
                    for (SelectionKey selectionKey : selected) {
                        dispatch(selectionKey);
                    }
                    selected.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void dispatch(SelectionKey k) {
            Runnable r = (Runnable) (k.attachment());
            if (r != null)
                r.run();
        }

        class Acceptor implements Runnable { // inner
            public void run() {
                try {
                    SocketChannel socketChannel = serverSocket.accept();
                    if (socketChannel != null) {
                        System.out.println(String.format("接收客户端%s的连接，并将socketChannel分发给Handler处理！", socketChannel.getRemoteAddress()));
                        new Handler(selector, socketChannel);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    final class Handler implements Runnable {

        final SocketChannel socketChannel;
        final SelectionKey selectionKey;

        ByteBuffer inputByteBuf = ByteBuffer.allocate(1024);

        Handler(Selector selector, SocketChannel socketChannel) throws IOException {
            this.socketChannel = socketChannel;
            socketChannel.configureBlocking(false);
            // Optionally try first read now
            selectionKey = socketChannel.register(selector, 0);
            selectionKey.attach(this);
            selectionKey.interestOps(SelectionKey.OP_READ);
            selector.wakeup();
        }

        public void run() {
            try {
                byte[] content = SocketChannelUtil.readChannelBytes(socketChannel, inputByteBuf);
                // 若读取不到数据却能监听到读事件，可能是因为连接已经断开了
                if (content == null) {
                    System.out.println("读取不到数据却能监听到读事件，可能是因为连接已经断开了，取消当前事件的监听！");
                    selectionKey.cancel();
                    return;
                }
                // 清理缓冲区
                inputByteBuf.clear();
                System.out.println(String.format("从客户端：%s读取到内容：%s", socketChannel.getRemoteAddress(), new String(content)));
                SocketChannelUtil.response200(socketChannel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String args[]) {
        try {
            BasicReactorNIOServer basicReactorNIOServer = new BasicReactorNIOServer(8888);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
