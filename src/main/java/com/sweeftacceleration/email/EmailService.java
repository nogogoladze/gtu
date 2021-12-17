package com.sweeftacceleration.email;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{

    @Autowired
    private final JavaMailSender mailSender;


    @Override
    public void send(String to, String activationCode) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText("გასააქტიურებლად ეწვიეთ ლინკს" + "http://localhost:8080/api/v1/system/user/active/" + " და შეიყვანეთ აქტივაციის კოდი! " + activationCode);
            helper.setTo(to);
            helper.setSubject("Active your account");
            helper.setFrom("fgg20532@gmail.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("failed to send email");
        }

    }
}
