package com.gipit.bookshop_backend.services.impl;

import com.gipit.bookshop_backend.services.IMailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService implements IMailService {

    private JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Override
    public void sendMail(String email, String to, String subject, String body) {
        MimeMessage message=mailSender.createMimeMessage();
        try{
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setFrom(email);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body,true);


        }catch (Exception e){
            e.printStackTrace();
        }
        mailSender.send(message);
    }
}
