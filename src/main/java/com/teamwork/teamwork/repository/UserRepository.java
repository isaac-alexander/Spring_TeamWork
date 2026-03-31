package com.teamwork.teamwork.repository;

import com.teamwork.teamwork.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    // checks if any user already has this email
    boolean existsByEmail(String email);

}
