package com.app.Migration.repo;

import com.app.Migration.Entity.AuthToken;
import com.app.Migration.Entity.TokenType;
import com.app.Migration.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
    Optional<AuthToken> findByUserAndType(User user, TokenType type);
}