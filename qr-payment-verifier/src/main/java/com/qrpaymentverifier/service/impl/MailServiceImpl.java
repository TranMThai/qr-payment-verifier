package com.qrpaymentverifier.service.impl;

import com.qrpaymentverifier.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    @Value("${spring.mail.username:}")
    private String username;

    private final JavaMailSender javaMailSender;

    @Override
    public void sendMail(String to, String subject, String content) {
        if (username == null || username.isBlank()) {
            return;
        }
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(username);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            javaMailSender.send(message);

        } catch (MessagingException e) {
            log.error("Error when sending mail", e);
        }
    }

}
