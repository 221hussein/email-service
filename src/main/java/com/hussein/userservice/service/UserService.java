package com.hussein.userservice.service;

import com.hussein.userservice.domain.User;

public interface UserService {

    User savedUser(User user);

    boolean verifyToken(String token);
}
