package com.teamwork.teamwork;

import com.teamwork.teamwork.entity.User;
import com.teamwork.teamwork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TeamworkApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TeamworkApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        User existingUser = userRepository.findByEmail("alex@gmail.com");

        if (existingUser == null) {
            User user1 = new User("Alex Isaac", "alex@gmail.com", "alex123", "admin");
            userRepository.save(user1);
        }

    }
}
