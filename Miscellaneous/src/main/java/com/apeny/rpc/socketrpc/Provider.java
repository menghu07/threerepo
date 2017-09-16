package com.apeny.rpc.socketrpc;

import com.apeny.domain.Person;
import com.apeny.service.infr.PersonService;
import com.apeny.service.infr.impl.PersonServiceImpl;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ahu on 2017年09月16日.
 */
public class Provider {
    private static final Map<String, Object> SERVICES = new HashMap<>();
    static {
        SERVICES.put(PersonService.class.getName(), new PersonServiceImpl());
    }
    public static void main(String[] args) {
        startup();
    }

    private static void startup() {
        try (ServerSocket serverSocket = new ServerSocket(9091)) {
            while (true) {
                Socket server = serverSocket.accept();
                InputStream inputStream = server.getInputStream();
                OutputStream outputStream = server.getOutputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                String serviceName = objectInputStream.readUTF();
                String methodName = objectInputStream.readUTF();
                //这里读完了，很有可能是错的
                int param = objectInputStream.readInt();
                param = Math.abs(param);
                Class<?> service = Class.forName(serviceName);
                Method method = service.getMethod(methodName, new Class[] {Integer.TYPE});
                Object serviceImpl = SERVICES.get(serviceName);
                Person p = null;
                if (serviceImpl == null || method == null) {
                    objectOutputStream.writeObject(p);
                    continue;
                }
                Person person = (Person) method.invoke(serviceImpl, new Object[] {param});
                objectOutputStream.writeObject(person);
                System.out.println("结束一次响应");
                objectInputStream.close();
                objectInputStream.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
