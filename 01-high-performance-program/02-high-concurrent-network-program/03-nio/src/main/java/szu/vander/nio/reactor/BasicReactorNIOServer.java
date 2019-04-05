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

    private static int MAXIN = 1024;

    private static int MAXOUT = 1024;

    private int port;

    public BasicReactorNIOServer(int port) throws IOException {
        this.port = port;
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
            SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            sk.attach(new Acceptor());
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
                    SocketChannel c = serverSocket.accept();
                    if (c != null)
                        new Handler(selector, c);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    final class Handler implements Runnable {
        final SocketChannel socketChannel;
        final SelectionKey selectionKey;
        ByteBuffer inputByteBuf = ByteBuffer.allocate(MAXIN);
        ByteBuffer output = ByteBuffer.allocate(MAXOUT);
        static final int READING = 0, SENDING = 1;
        int state = READING;

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
                System.out.println(String.format("从客户端：%s读取到内容：%s", socketChannel.getRemoteAddress(), new String(content)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
