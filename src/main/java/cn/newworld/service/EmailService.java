package cn.newworld.service;

import cn.newworld.util.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


/**
 * 邮件业务
 */
public class EmailService {


    // 发件人邮箱地址
    private static final String fromEmail = "newworldstudio@163.com";
    // 发件人邮箱密码（如果是授权码登录，使用授权码）
    private static final String emailPassword = "FDTEZCJBVZGFYCUB";
    // 邮件主题

    // 邮件内容
    String body = "This is a test email from JavaMail.";
    public static String sendEmail(String to, String subject, String body){
        Properties props = new Properties();
        props.setProperty("mail.host", "smtp.163.com");
        props.setProperty("mail.transport.protocol", "SMTP");
        props.setProperty("mail.smtp.auth", "true");

        Authenticator authenticator = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, emailPassword); // 邮件账号和授权码，注意不是密码。
            }

        };
        Session session = Session.getInstance(props, authenticator);
        MimeMessage mess = new MimeMessage(session);
        try {
            mess.setFrom(new InternetAddress(fromEmail)); // 设置邮件的发件人
            mess.setRecipients(Message.RecipientType.TO, to); // 设置收件人
            mess.setSubject(subject); // 设置邮件标题
            mess.setContent(body, "text/html;charset=utf-8"); // 设置邮件内容和格式

            Transport.send(mess);
        } catch (MessagingException e) {
            return "发送邮件失败, 原因:" + e.getMessage();
        }
        return "发送邮件成功！接收人：" + to;
    }
}
