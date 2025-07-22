package com.app_bancaria.my_bnl_application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Async
    public void sendEmail(String email, String message){
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setFrom(sender);
        emailMessage.setTo(email);
        emailMessage.setText(message);

        mailSender.send(emailMessage);
    }
}
