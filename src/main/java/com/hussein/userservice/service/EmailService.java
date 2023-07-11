package com.hussein.userservice.service;

public interface EmailService {
    void sendSimpleMailService(String name, String to, String token);
    void sendMimeMessageWithAttachements(String name, String to, String token);
    void sendMimeMessageWithEmbeddedImages(String name, String to, String token);
    void sendMimeMessageWithEmbeddedFiles(String name, String to, String token);
    void sendHtmlEmail(String name, String to, String token);
    void sendHtmlEmailWithEmbeddedFiles(String name, String to, String token);

}
