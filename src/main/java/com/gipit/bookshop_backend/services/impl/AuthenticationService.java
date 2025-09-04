package com.gipit.bookshop_backend.services.impl;

import com.gipit.bookshop_backend.components.JwtTokenUtil;
import com.gipit.bookshop_backend.exception.AppException;
import com.gipit.bookshop_backend.exception.ErrorCode;
import com.gipit.bookshop_backend.models.Token;
import com.gipit.bookshop_backend.services.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public String login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        if(!authentication.isAuthenticated()) {
           throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return jwtTokenUtil.generateToken(username, Token.TokenType.ACCESS);
    }
}
