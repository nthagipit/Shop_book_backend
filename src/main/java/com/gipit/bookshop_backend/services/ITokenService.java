package com.gipit.bookshop_backend.services;

import com.gipit.bookshop_backend.dto.request.LogoutRequest;
import com.gipit.bookshop_backend.models.Token;

import java.util.Optional;

public interface ITokenService {
    void logout(LogoutRequest logoutRequest);
    Token saveToken(Token token);
    Token getToken(String token);
    void purgeExpiredTokens();
    String refreshToken(String refreshToken);
}
