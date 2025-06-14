package com.example.FindJobIT.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.FindJobIT.domain.PasswordResetToken;
import com.example.FindJobIT.domain.User;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
    PasswordResetToken findByToken(String token);
    PasswordResetToken findByUser(User user);
}
