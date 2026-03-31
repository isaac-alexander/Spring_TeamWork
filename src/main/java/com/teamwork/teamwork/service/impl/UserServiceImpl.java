package com.teamwork.teamwork.service.impl;

import com.teamwork.teamwork.entity.User;
import com.teamwork.teamwork.exception.EmailAlreadyExistsException;
import com.teamwork.teamwork.repository.UserRepository;
import com.teamwork.teamwork.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user) {
        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            // Throw custom exception instead of letting the database crash
            throw new EmailAlreadyExistsException("Email already exists");
        }

        // Save user if email is unique
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}