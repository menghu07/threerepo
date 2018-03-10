package com.apeny.io.socket.slowio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Random;

/**
 * write 阻塞在client没有跟上server write步伐时
 * Created by apeny on 2018/2/27.
 */
public class SocketServer {
    public static void main(String[] args) throws IOException {
        System.out.println("i have established 9021");
        ServerSocket serverSocket = new ServerSocket(9021);
        while (true) {
            try {
                Socket server = serverSocket.accept();
                System.out.println(server + "my socket reuse address: " + server.getReuseAddress() + " get localPort" + server.getLocalPort());
                server.setKeepAlive(true);
                server.setSoTimeout(60 * 1000 * 1000);
                InputStream inputStream = server.getInputStream();
                OutputStream outputStream = server.getOutputStream();
                int length = 99999999;
                byte[] bytes = new byte[length];
                String toClient = ", hello i send to you my socket is 9020";
                char[] chars = toClient.toCharArray();
                for (int i = 0; i < length; i++) {
                    bytes[i] = (byte) chars[new Random().nextInt(chars.length)];
                }
                System.out.println("start write to io: " + new Date());
//                inputStream.read(bytes);
                outputStream.write(bytes);
                server.close();
                System.out.println("i have receive some message from client: " + new Date());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
