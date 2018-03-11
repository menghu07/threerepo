package com.apeny.io.socket.slowio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * connection timeout 条件：目标地址不可达
 * connection refused 条件：目标地址可达，但没有进程监听端口
 * Created by apeny on 2018/2/27.
 */
public class SocketClient {
    public static void main(String[] args) {
        Socket client = new Socket();
        try {
            client.connect(new InetSocketAddress("127.0.0.1", 9021), 30000);
            System.out.println("my socket : " + client.getPort() + " get localPort" + client.getLocalPort() + "receive buffer size: " + client.getReceiveBufferSize());
            client.setSoTimeout(60 * 1000 * 1000);
            System.out.println(client.getSoLinger());
            client.setSoLinger(true, 3600);
            OutputStream outputStream = client.getOutputStream();
            // outputStream.write( (System.nanoTime() + "," + Thread.currentThread() + "my name huhu, make friends?").getBytes());
            InputStream inputStream = client.getInputStream();
            byte[] bytes = new byte[10];
            int count;
            while ((count = inputStream.read(bytes)) != -1) {
                System.out.println("i have receive some message from server" + new String(bytes, 0, count));
                TimeUnit.MICROSECONDS.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
