package com.app;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 * Created by monis on 2018/8/24.
 */
public class SendMail {
    public static void main(String[] args) {
        sendMail();
    }

    private static void sendMail() {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("config.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String hostName = properties.getProperty("email.host");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        String fromEmailAddress = properties.getProperty("email.address");
        String toEmailAddress = properties.getProperty("to.email.address");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.host", hostName);
        Session session = Session.getInstance(properties);
        System.out.println("创建session成功, time: " + new Date());
        session.setDebug(true);

        MimeMessage message = getMessage(session, fromEmailAddress, toEmailAddress);
        try {
            Transport transport = session.getTransport();
            System.out.println("transport 创建成功, time: " + new Date());
            transport.connect(username, password);
            System.out.println("创建连接成功, time: " + new Date());
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("发送邮件结束, time: " + new Date());
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    private static MimeMessage getMessage(Session session, String fromEmailAddress, String toEmailAddress) {
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(fromEmailAddress));
            message.setRecipients(Message.RecipientType.TO, toEmailAddress);
            message.setSubject("测试邮件", "UTF-8");
            message.setContent("哪里哪里，都是不同的", "text/html;charset=utf-8");
            message.setSentDate(new Date());
            return message;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
