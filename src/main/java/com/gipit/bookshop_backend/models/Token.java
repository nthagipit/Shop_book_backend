package com.gipit.bookshop_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="token")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="token_id")
    private int id;

    @Column(name="token")
    private String token;

    @Column(name="token_type",length =50)
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @Column(name="expiration_date")
    private LocalDateTime expirationDate;

    private boolean revoked;
    private boolean expired;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;


    public enum TokenType{
        ACCESS,REFRESH
    }
}

