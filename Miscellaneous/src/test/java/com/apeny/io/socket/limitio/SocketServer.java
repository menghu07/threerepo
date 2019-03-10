package com.apeny.io.socket.limitio;

import com.apeny.environment.SystemEnvironment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * ServerSocket在accept之前就已经有client连接到server，并且可以发送数据给server
 * close client 可以server继续执行写/读; client 关闭一分钟左右后 Software caused connection abort: socket write error，因为整个连接关闭了
 * ServerSocket close 在获取socket之前， client write 抛异常 connection reset by peer; 抛异常connection reset
 * ServerSocket close 在获取socket之后 write/read 没有影响，正常运行
 * System.exit(1) 客户端报Connect reset异常
 * Created by apeny on 2018/2/27.
 */
public class SocketServer {
    public static void main(String[] args) throws IOException {
        System.out.println("i have established 9020");
        while (true) {
            try {
                ServerSocket serverSocket = new ServerSocket();
                ServerSocket backupSocket = new ServerSocket();
                System.out.println(serverSocket + "my socket reuse address: " + serverSocket.getReuseAddress() + " get localPort" + serverSocket.getLocalPort());
                //先设置可重新绑定
                serverSocket.bind(new InetSocketAddress(9020));
                Socket server = serverSocket.accept();
                System.out.println("server default read timeout: " + server.getSoTimeout());
//                serverSocket.close();
//                System.out.println("first server is closed");
//                backupSocket.setReuseAddress(true);
//                backupSocket.bind(new InetSocketAddress(9020));
//                backupSocket.accept();
//                System.out.println(server + "my socket reuse address: " + server.getReuseAddress() + " get localPort" + server.getLocalPort());
//                server.setKeepAlive(true);
                //没有数据可读时会超时
                server.setSoTimeout(3000 * 1000);
                InputStream inputStream = server.getInputStream();
                OutputStream outputStream = server.getOutputStream();
                byte[] bytes = new byte[5000];
                String toClient = ", hello i send to you my socket is 9020";
//                outputStream.write(toClient.getBytes());
                outputStream.write(bytes);
                System.out.println("i have receive some message from client");
                System.out.println("begintime " + new Date());
//                while (true) {
                    int count = inputStream.read(bytes);
//                    if (count == -1) {
//                        break;
//                    }
//                System.out.println("i read data from client: hhhhh" + count);
//                }
                System.out.println("close server port");
            } catch (Exception ex) {
                System.out.println("endtime " + new Date());
                ex.printStackTrace();
//                System.exit(1);
            }
            try {
                System.out.println("server sleep in here");
                TimeUnit.SECONDS.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
