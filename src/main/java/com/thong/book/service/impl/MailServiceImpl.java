package com.thong.book.service.impl;

import com.thong.book.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendActiveUserMail(String email) {
        try {

            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setTo(email);
            mailMessage.setFrom(sender);
            mailMessage.setText("Has create account!");
            mailMessage.setSubject("Book Create account!");

            mailSender.send(mailMessage);
        } catch (Exception e) {
            throw new RuntimeException("Can't send mail to " + email);
        }
    }
}
