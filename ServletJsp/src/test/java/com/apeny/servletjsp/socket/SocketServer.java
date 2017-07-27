package com.apeny.servletjsp.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by apeny on 2017/7/26.
 */
public class SocketServer {
    public static void main(String[] args) {
        try {
            System.out.println("i have established 9039");
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
                    inputStream.read(bytes);
                    String toClient = new String(bytes) + ", hello i send to you my socket is 9020";
                    outputStream.write(toClient.getBytes());
                    outputStream.flush();
                    System.out.println("i have receive some message from client");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
