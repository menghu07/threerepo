package com.apeny.rpc.socketrpc;

import com.apeny.domain.Person;
import com.apeny.service.infr.PersonService;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by ahu on 2017年09月16日.
 */
public class Consumer {
    public static void main(String[] args) throws Exception {
        sendToProvider();
    }

    public static void sendToProvider() throws Exception {
        try (Socket socket = new Socket("localhost", 9091)) {
            socket.setSoTimeout(6_000_000);
//            socket.connect(new InetSocketAddress("localhost", 9091), 60_000);
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            int id = -1;
            //接口名称
            objectOutputStream.writeUTF(PersonService.class.getName());
            objectOutputStream.writeUTF("getPerson");
            //Person.id
            objectOutputStream.writeInt(id);
            objectOutputStream.flush();
            Person person = (Person) objectInputStream.readObject();
            System.out.println("read from Provider" + person);
            objectOutputStream.close();
            objectInputStream.close();

        }
    }
}
