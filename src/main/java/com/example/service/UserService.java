package com.example.service;

import com.example.model.User;

public interface UserService {
    User findByChatId(long chatId);

    void updateUser(User user);
}
