package com.hussein.userservice.service.impl;

import com.hussein.userservice.domain.Confirmation;
import com.hussein.userservice.domain.User;
import com.hussein.userservice.repository.ConfirmationRepository;
import com.hussein.userservice.repository.UserRepository;
import com.hussein.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final EmailServiceImpl emailService;

    private final UserRepository userRepository;

    private final ConfirmationRepository confirmationRepository;
    @Override
    public User savedUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw  new RuntimeException("Email already exists");
        }
        user.setEnabled(false);
        userRepository.save(user);

        Confirmation confirmation = new Confirmation(user);
        confirmationRepository.save(confirmation);

//    todo send email to user with token
        //emailService.sendSimpleMailService(user.getName(),user.getEmail(), confirmation.getToken());
        //send Mime message with attachements
        //emailService.sendMimeMessageWithAttachements(user.getName(),user.getEmail(),confirmation.getToken());
        //emailService.sendMimeMessageWithEmbeddedImages(user.getName(), user.getEmail(), confirmation.getToken());
        //emailService.sendMimeMessageWithEmbeddedFiles(user.getName(), user.getEmail(), confirmation.getToken());
        emailService.sendHtmlEmail(user.getName(), user.getEmail(), confirmation.getToken());

        return user;
    }

    @Override
    public boolean verifyToken(String token) {
        Confirmation confirmation = confirmationRepository.findByToken(token);
        User user = userRepository.findByEmailIgnoreCase(confirmation.getUser().getEmail());
        user.setEnabled(true);
        userRepository.save(user);
        //confirmationRepository.delete(confirmation);
        return Boolean.TRUE;
    }
}
