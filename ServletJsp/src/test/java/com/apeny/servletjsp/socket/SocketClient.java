package com.apeny.servletjsp.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * Created by apeny on 2017/7/26.
 */
public class SocketClient {
    public static void main(String[] args) {
        Socket client = new Socket();
        try {
            client.connect(new InetSocketAddress("127.0.0.1", 9020));
            System.out.println("my socket : " + client.getPort() + " get localPort" + client.getLocalPort());
            client.setSoTimeout(60 * 1000 * 1000);
            OutputStream outputStream = client.getOutputStream();
            outputStream.write( (System.nanoTime() + "," + Thread.currentThread() + "my name huhu, make friends?").getBytes());
            outputStream.flush();
            InputStream inputStream = client.getInputStream();
            byte[] bytes = new byte[1024];
            inputStream.read(bytes);
            System.out.println("i have receive some message from server");

            //连接到另一个Server
            //已经连接了！！！错误
            //!client.connect(new InetSocketAddress("127.0.0.1", 9021));
            OutputStream outputStream2 = client.getOutputStream();
            outputStream.write( (System.nanoTime() + "," + Thread.currentThread() + "my name huhu, make friends?").getBytes());
            outputStream.flush();
            InputStream inputStream2 = client.getInputStream();
            byte[] bytes2 = new byte[1024];
            inputStream.read(bytes);
            int i = 0;
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println("client sleep here" + i++ + " thread: " + Thread.currentThread());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
