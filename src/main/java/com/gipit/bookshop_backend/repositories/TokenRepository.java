package com.gipit.bookshop_backend.repositories;

import com.gipit.bookshop_backend.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Token findByToken(String token);
    long deleteByExpirationDateBefore(LocalDateTime time);
}
