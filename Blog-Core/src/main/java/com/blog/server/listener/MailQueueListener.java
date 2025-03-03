package com.blog.server.listener;

import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "mail")
public class MailQueueListener {

    @Resource
    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String username;

    @RabbitHandler
    public void sendMail(Map<String, Object> data){
        String email = data.get("email").toString();
        Integer code = (Integer) data.get("code");
        String type = data.get("type").toString();
        SimpleMailMessage message = switch (type) {
            case "register" -> createMail("注册验证码", "您的注册验证码为：" + code + "有效时间5分钟", email);

            default -> null;
        };
        if (message == null) return;
        sender.send(message);
    }

    private SimpleMailMessage createMail(String title, String content, String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(title);
        message.setText(content);
        message.setTo(email);
        message.setFrom(username);
        return message;
    }
}
