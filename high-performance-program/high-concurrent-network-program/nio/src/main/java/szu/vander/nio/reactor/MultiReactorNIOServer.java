package szu.vander.nio.reactor;

import szu.vander.nio.SocketChannelUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: Vander
 * @Date: 2019/4/4
 * @Description: Reactor:通过分配给特定的Handler来响应IO事件
 * Handler：执行非阻塞任务
 * 不同于基础的Rector模式：
 * 1、将Handler放到线程池中处理
 * 2、使用多Reactor，mainReactor分发接收连接，subReactor分发读写请求
 * 3、一个Reactor对应多个Selector
 */
public class MultiReactorNIOServer {

    private final static int SUB_REACTOR_THREAD_NUM = 5;

    private ReactorThread[] mainReactorThreads;

    private ReactorThread[] subReactorThreads = new SubReactorThread[SUB_REACTOR_THREAD_NUM];

    private int[] ports;

    public MultiReactorNIOServer(int[] ports) throws Exception {
        this.ports = ports;
        mainReactorThreads = new MainReactorThread[ports.length];
        initReactorThreads();
        for (Thread thread : mainReactorThreads) {
            thread.start();
        }
    }

    private void initReactorThreads() throws Exception {
        System.out.println(String.format("线程%s：开始创建mainReactor线程组和subReactor线程组！", Thread.currentThread().toString()));
        for (int i = 0; i < mainReactorThreads.length; i++) {
            mainReactorThreads[i] = new MainReactorThread(ports[i]);
        }
        for (int i = 0; i < subReactorThreads.length; i++) {
            subReactorThreads[i] = new SubReactorThread();
        }
    }

    abstract class ReactorThread extends Thread {

        final Selector selector;

        public ReactorThread() throws IOException {
            selector = Selector.open();
        }

        abstract void dispatch(SelectionKey selectionKey) throws Exception;

        public void run() {
            try {
                System.out.println(String.format("线程%s：开始执行！", Thread.currentThread().toString()));
                while (!currentThread().isInterrupted()) {
                    selector.select();
                    // 分发任务
                    Set<SelectionKey> selected = selector.selectedKeys();
                    for (SelectionKey selectionKey : selected) {
                        dispatch(selectionKey);
                    }
                    selected.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public SelectionKey register(SelectableChannel channel) throws Exception {
            SelectionKey selectionKey = channel.register(selector, 0, channel);
            return selectionKey;
        }

    }

    class MainReactorThread extends ReactorThread {

        private ServerSocketChannel serverSocketChannel;

        public MainReactorThread(int port) throws Exception {
            // 创建ServerSocketChannel
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            // 将serverSocketChannel注册到selector
            serverSocketChannel.bind(new InetSocketAddress(port));
            SelectionKey selectionKey = register(serverSocketChannel);
            selectionKey.interestOps(SelectionKey.OP_ACCEPT);
            System.out.println(String.format("线程%s：新建mainReactorThread，绑定端口为%s！", Thread.currentThread().toString(), port));
        }

        // 分发任务给SubReactor
        @Override
        void dispatch(SelectionKey selectionKey) throws Exception {
            System.out.println(String.format("mainReactor线程组中的线程%s：开始分配任务！", Thread.currentThread().toString()));
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.attachment();
            SocketChannel socketChannel = serverSocketChannel.accept();
            System.out.println(String.format("线程%s：接收客户端%s的连接，并将socketChannel分发给SubReactor处理！",
                    Thread.currentThread().toString(), socketChannel.getRemoteAddress()));
            socketChannel.configureBlocking(false);
            // 从SubThreads线程组中随机获取一个来处理读事件
            ReactorThread subReactorThread = subReactorThreads[new Random().nextInt(subReactorThreads.length)];
            SelectionKey readWriteKey = subReactorThread.register(socketChannel);
            readWriteKey.interestOps(SelectionKey.OP_READ);
            subReactorThread.start();
        }

    }

    class SubReactorThread extends ReactorThread {

        private ExecutorService handlersPool = Executors.newFixedThreadPool(10);

        private ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        public SubReactorThread() throws IOException {
        }

        // 执行基本的读写并将读到的内容给Handler
        public void dispatch(SelectionKey selectionKey) {
            System.out.println(String.format("SubReactor线程组中的线程%s：开始分配任务！", Thread.currentThread().toString()));
            try {
                while (!Thread.interrupted()) {
                    SocketChannel readWriteSocketChannel = (SocketChannel) selectionKey.attachment();
                    byte[] content = SocketChannelUtil.readChannelBytes(readWriteSocketChannel, byteBuffer);
                    if(content == null){
                        selectionKey.cancel();
                        readWriteSocketChannel.close();
                        break;
                    }
                    handlersPool.execute(() -> {
                        try {
                            System.out.println(String.format("%s线程-接收到来自%s的信息：%s",
                                    Thread.currentThread().toString(), readWriteSocketChannel.getRemoteAddress(), new String(content)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                    SocketChannelUtil.response200(readWriteSocketChannel);

                    if (!readWriteSocketChannel.isOpen()) {
                        selectionKey.cancel();
                    }
                    byteBuffer.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String args[]) {
        try {
            System.out.println(String.format("主线程%s：开启MultiReactorNIOServer", Thread.currentThread().toString()));
            new MultiReactorNIOServer(new int[]{8080});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
