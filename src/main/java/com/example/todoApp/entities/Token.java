package com.example.todoApp.entities;

import com.example.todoApp.entities.enums.TokenType;
import jakarta.persistence.*;
@Entity
public class Token extends BaseEntity {
    protected Token() {}
    public  Token(
            String token,
            TokenType tokenType,
            boolean revoked,
            boolean expired,
            UserAccount user
    ){
        this.token = token;
        this.tokenType = tokenType;
        this.revoked = revoked;
        this.expired = expired;
        this.user = user;
    }
    @Column(unique = true)
    public String token;
    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;
    public boolean revoked;
    public boolean expired;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserAccount user;
}
