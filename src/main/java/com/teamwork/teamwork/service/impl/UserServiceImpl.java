package com.teamwork.teamwork.service.impl;

import com.teamwork.teamwork.entity.User;
import com.teamwork.teamwork.repository.UserRepository;
import com.teamwork.teamwork.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user); // built-in JPA method
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
