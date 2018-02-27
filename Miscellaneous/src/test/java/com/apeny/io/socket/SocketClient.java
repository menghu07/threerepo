package com.apeny.io.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by apeny on 2018/2/27.
 */
public class SocketClient {
    public static void main(String[] args) {
        Socket client = new Socket();
        try {
            client.connect(new InetSocketAddress("127.0.0.1", 9020));
            System.out.println("my socket : " + client.getPort() + " get localPort" + client.getLocalPort());
            client.setSoTimeout(60 * 1000 * 1000);
            OutputStream outputStream = client.getOutputStream();
           // outputStream.write( (System.nanoTime() + "," + Thread.currentThread() + "my name huhu, make friends?").getBytes());
            InputStream inputStream = client.getInputStream();
            byte[] bytes = new byte[1024];
            inputStream.read(bytes);
            System.out.println("i have receive some message from server" + new String(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
