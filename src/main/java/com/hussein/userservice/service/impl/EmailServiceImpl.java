package com.hussein.userservice.service.impl;

import com.hussein.userservice.service.EmailService;
import com.hussein.userservice.utils.EmailUtils;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.lang.String.*;
import java.util.Map;

import static com.hussein.userservice.utils.EmailUtils.getVerificationUrl;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    public static final String UTF_8_ENCODING = "UTF-8";
    public static final String EMAIL_TEMPLATE = "emailtemplate";

    private final TemplateEngine templateEngine;
    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender sender;

    private MimeMessage getMimeMessage () {
        return sender.createMimeMessage();
    }
    private String getContentId (String filename) {
        return "<" + filename + ">";
    }
    @Override
    @Async
    public void sendSimpleMailService(String name, String to, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("NEW USER ACCOUNT VERIFICATION");
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setText(EmailUtils.getEmailMessage(name, host, token));
            sender.send(message);
        }catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    @Async
    public void sendMimeMessageWithAttachements(String name, String to, String token) {
        try {
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true,UTF_8_ENCODING);
            helper.setPriority(1); //for setting the priority to high
            helper.setSubject("NEW USER ACCOUNT VERIFICATION");
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(EmailUtils.getEmailMessage(name, host, token));
            // Add attachements we can duplicate it if we want send many file
            FileSystemResource fileImage = new FileSystemResource(new File(System.getProperty("user.home") +"/Downloads/fileImage.jpg"));
            helper.addAttachment(fileImage.getFilename(), fileImage);
            sender.send(message);
        }catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    @Async
    public void sendMimeMessageWithEmbeddedImages(String name, String to, String token) {
        try {
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true,UTF_8_ENCODING);
            helper.setPriority(1); //for setting the priority to high
            helper.setSubject("NEW USER ACCOUNT VERIFICATION");
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(EmailUtils.getEmailMessage(name, host, token));
            // Add attachements we can duplicate it if we want send many file
            FileSystemResource fileImage = new FileSystemResource(new File(System.getProperty("user.home") +"/Downloads/fileImage.jpg"));
            // we must use addInline instead of add attachememnt
            helper.addInline(getContentId(fileImage.getFilename()), fileImage);
            sender.send(message);
        }catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }

    }

    @Override
    @Async
    public void sendMimeMessageWithEmbeddedFiles(String name, String to, String token) {
        try {
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true,UTF_8_ENCODING);
            helper.setPriority(1); //for setting the priority to high
            helper.setSubject("NEW USER ACCOUNT VERIFICATION");
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(EmailUtils.getEmailMessage(name, host, token));
            // Add attachements we can duplicate it if we want send many file
            FileSystemResource fileImage = new FileSystemResource(new File(System.getProperty("user.home") +"/Downloads/fileImage.jpg"));
            // we must use addInline instead of add attachememnt
            helper.addInline(getContentId(fileImage.getFilename()), fileImage);
            sender.send(message);
        }catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    @Async
    public void sendHtmlEmail(String name, String to, String token) {
        try {
            Context context = new Context();
//            context.setVariable("name",name);
//            context.setVariable("url", getVerificationUrl(host,token));
            context.setVariables(Map.of("name",name,"url",getVerificationUrl(host,token)));
            String text = templateEngine.process(EMAIL_TEMPLATE, context);
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true,UTF_8_ENCODING);
            helper.setPriority(1); //for setting the priority to high
            helper.setSubject("NEW USER ACCOUNT VERIFICATION");
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(text,true);
            sender.send(message);
        }catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    @Async
    public void sendHtmlEmailWithEmbeddedFiles(String name, String to, String token) {

    }
}
