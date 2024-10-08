package com.example.service;

import com.example.Entity.MailEntity;
import com.example.repository.MailHistoryRepository;
import com.example.util.JWTUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {


    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MailHistoryRepository mailRepository;

    @Value("shokhjumaboyev77@mail.ru")
    private String fromMail;

    @Value("${server.url}")
    private String serverUrl;

    void sendSimpleMail(String toAccount, String subject, String text) {
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            msg.setFrom(fromMail);
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(toAccount);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(msg);
            MailEntity entity = new MailEntity();
            entity.setMessage(text);
            entity.setMail(toAccount);
            mailRepository.save(entity);
        }catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendEmailVerification(String toAccount, String name, Integer id){
        String  jwt = JWTUtil.encodeEmailJwt(id);
        String url = serverUrl+"/auth/verification/email"+jwt;

        StringBuilder builder =new StringBuilder();
        builder.append(String.format(" <h1 style=\"text-align: center\">Hello %s</h1>",name));
        builder.append(String.format("<a href=\"%s\">\n" +
                "            CLick the link to complete Registration</a>",url));

        sendSimpleMail  (toAccount, "Registration Link", builder.toString());
    }

}
