package com.panda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.panda.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
	PasswordResetToken findByToken(String token);
}
