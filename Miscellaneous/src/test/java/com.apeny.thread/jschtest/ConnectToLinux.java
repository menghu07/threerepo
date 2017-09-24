package com.apeny.thread.jschtest;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.junit.Test;

/**
 * Created by apeny on 2017年08月25日.
 */
public class ConnectToLinux {

    @Test
    public void testConnectToLinux() throws JSchException {
        String userName = "yizhifu";
        String password = "yizhifu123";
        String host = "192.168.121.61";
        int port = 22;
        JSch jsch = new JSch();
        Session session = jsch.getSession(userName,host,port);
        session.setPassword(password);
        System.out.println("session : " + session);
    }
}
