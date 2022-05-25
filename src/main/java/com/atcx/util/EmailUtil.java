package com.atcx.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <p>description<p/>
 *
 * @author likun
 * @date： 2022/5/25 16:32
 */
@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    private static JavaMailSender mailSender;

    private static String fromUser;

    @Value("${spring.mail.username}")
    private String from;

    @PostConstruct
    public void init() {
        fromUser = this.from;
        mailSender = this.javaMailSender;
    }

    public static void sendSimpleMail(MailSendDTO dto) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromUser);
        simpleMailMessage.setTo(dto.getTo());
        simpleMailMessage.setSubject(dto.getSubject());
        simpleMailMessage.setText(dto.getContent());
        mailSender.send(simpleMailMessage);
    }
}
