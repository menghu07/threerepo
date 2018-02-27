package com.apeny.io.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ServerSocket在accept之前就已经有client连接到server，并且可以发送数据给server
 * close client 可以server继续执行写/读
 * ServerSocket close 在获取socket之前， client write 抛异常 connection reset by peer; 抛异常connection reset
 * ServerSocket close 在获取socket之后 write/read 没有影响，正常运行
 * Created by apeny on 2018/2/27.
 */
public class SocketServer {
    public static void main(String[] args) throws IOException {
        System.out.println("i have established 9020");
        ServerSocket serverSocket = new ServerSocket(9020);
        while (true) {
            try {
                System.out.println(serverSocket + "my socket reuse address: " + serverSocket.getReuseAddress() + " get localPort" + serverSocket.getLocalPort());
                Socket server = serverSocket.accept();
                System.out.println(server + "my socket reuse address: " + server.getReuseAddress() + " get localPort" + server.getLocalPort());
                server.setKeepAlive(true);
                server.setSoTimeout(60 * 1000 * 1000);
                InputStream inputStream = server.getInputStream();
                OutputStream outputStream = server.getOutputStream();
                byte[] bytes = new byte[1024];
//                inputStream.read(bytes);
                String toClient = ", hello i send to you my socket is 9020";
                outputStream.write(toClient.getBytes());
                System.out.println("i have receive some message from client");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
