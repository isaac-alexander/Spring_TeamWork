package com.teamwork.teamwork.service;

import com.teamwork.teamwork.entity.User;

public interface UserService {


    // register new user
    void saveUser(User user);

    // login user
    User findByEmail(String email);
}