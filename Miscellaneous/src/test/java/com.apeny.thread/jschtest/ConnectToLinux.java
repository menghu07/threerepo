package com.apeny.thread.jschtest;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.junit.Test;

import java.util.Properties;

/**
 * Created by apeny on 2017年08月25日.
 */
public class ConnectToLinux {
    ConnectToLinux() {

    }

    @Test
    public void testConnectToLinux() throws JSchException {
        Properties p = System.getProperties();

        String userName = "yizhifu";
        String password = "yizhifu123";
        String host = "192.168.121.61";
        int port = 22;
        JSch jsch = new JSch();
        Session session = jsch.getSession(userName, host, port);
        session.setPassword(password);
        System.out.println("session : " + session);
    }
}

class SubClass extends ConnectToLinux {
    public SubClass() {
        super();
    }
}