package com.gipit.bookshop_backend.services.impl;

import com.gipit.bookshop_backend.components.JwtTokenUtil;
import com.gipit.bookshop_backend.dto.request.LogoutRequest;
import com.gipit.bookshop_backend.exception.AppException;
import com.gipit.bookshop_backend.exception.ErrorCode;
import com.gipit.bookshop_backend.models.Token;
import com.gipit.bookshop_backend.models.User;
import com.gipit.bookshop_backend.repositories.TokenRepository;
import com.gipit.bookshop_backend.services.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TokenService implements ITokenService {
    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Override
    public void logout(LogoutRequest logoutRequest) {
        String token=logoutRequest.getToken();
        Token tokenRevoke= tokenRepository.findByToken(token);
        if(tokenRevoke==null){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        tokenRevoke.setRevoked(true);
        tokenRevoke.setExpired(true);
        tokenRepository.save(tokenRevoke);
    }

    @Override
    public Token saveToken(Token token) {
        return Optional.ofNullable(tokenRepository.save(token)).orElseThrow(()->new RuntimeException("Could not save token"));
    }

    @Override
    public Token getToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    @Scheduled(cron = "0 0 * * * *")
    public void purgeExpiredTokens() {
        LocalDateTime refreshDeadline=LocalDateTime.now().minusSeconds(jwtTokenUtil.getREFRESHABLE_DURATION());
        tokenRepository.deleteByExpirationDateBefore(refreshDeadline);
    }

    @Override
    public String refreshToken(String refreshToken) {
        Token token=tokenRepository.findByToken(refreshToken);
        if(token==null  || token.isRevoked()){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        if(!jwtTokenUtil.isTokenExpired(token.getToken())){
            throw new AppException(ErrorCode.TOKEN_NOT_EXPIRED_YET);
        }

        if(!jwtTokenUtil.isTokenRefreshable(token.getToken())){
            throw new AppException(ErrorCode.TOKEN_EXPIRED);
        }
        token.setRevoked(true);
        token.setExpired(true);
        saveToken(token);
        User user=token.getUser();
        return jwtTokenUtil.generateToken(user.getUsername(), Token.TokenType.ACCESS) ;
    }


}
