package com.gipit.bookshop_backend.controller;

import com.gipit.bookshop_backend.dto.request.AuthenticationRequest;
import com.gipit.bookshop_backend.dto.request.LogoutRequest;
import com.gipit.bookshop_backend.dto.request.RefreshRequest;
import com.gipit.bookshop_backend.dto.response.ApiResponse;
import com.gipit.bookshop_backend.dto.response.AuthenticationResponse;
import com.gipit.bookshop_backend.services.impl.AuthenticationService;
import com.gipit.bookshop_backend.services.impl.TokenService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/auth")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        String token=authenticationService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        log.info(token);
        AuthenticationResponse authenticationResponse= AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
        return ApiResponse.<AuthenticationResponse>builder()
                .data(authenticationResponse)
                .build();
    }
    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest logoutRequest){
        tokenService.logout(logoutRequest);
        return ApiResponse.<Void>builder()
                .build();
    }
    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refresh(@RequestBody RefreshRequest refreshRequest){
        String token=tokenService.refreshToken(refreshRequest.getToken());
        AuthenticationResponse authenticationResponse= AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
        return ApiResponse.<AuthenticationResponse>builder()
                .data(authenticationResponse)
                .build();
    }

}
