package com.example.todoApp.repository;

import com.example.todoApp.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
    @Query(value = """
            SELECT t \s
            FROM Token t \s
            JOIN UserAccount ua \s
            on t.user.id = ua.id \s
            WHERE ua.id = :id and (t.expired = false or t.revoked = false) \s
            """)
    List<Token> findAllValidTokenByUser(Long id);
}
