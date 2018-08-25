package com.apeny.mail;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
        String hostName = "smtp.163.com";
        String userName = "munis07";
        String password = "munis123654";
        String fromEmailAddress = "munis07@163.com";
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.host", hostName);

        Session session = Session.getInstance(properties);
        session.setDebug(false);
        MimeMessage message = getMessage(session, fromEmailAddress, "2582666800@qq.com");
        try {
            Transport transport = session.getTransport();
            transport.connect(userName, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
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
