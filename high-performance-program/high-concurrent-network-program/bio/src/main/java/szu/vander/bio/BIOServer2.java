package szu.vander.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: Vander
 * @Date: 2019/3/31
 * @Description:
 */
public class BIOServer2 {

    private static ServerSocket serverSocket;

    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    public static void main(String args[]) throws IOException {
        startServer();
    }

    public static void startServer() throws IOException {
        serverSocket = new ServerSocket(8080);
        System.out.println("服务器启动成功");
        while(!serverSocket.isClosed()){
            Socket request = serverSocket.accept();// 阻塞
            System.out.println("收到新连接 : " + request.toString());
            createThread(request);
        }
    }

    public static void createThread(Socket request){
        threadPool.execute(()->{
            try {
                // 接收数据、打印
                BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
                boolean hasPrintFrom = false;
                String msg;
                while ((msg = reader.readLine()) != null) { // 没有数据，阻塞
                    if(!hasPrintFrom){
                        System.out.println(String.format("收到来自%s的数据：", request.toString()));
                        hasPrintFrom = true;
                    }
                    if (msg.length() == 0) {
                        break;
                    }
                    System.out.println(msg);
                }
                // 响应结果 200
                OutputStream outputStream = request.getOutputStream();
                outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
                outputStream.write("Content-Length: 11\r\n\r\n".getBytes());
                outputStream.write("Hello World".getBytes());
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    request.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
