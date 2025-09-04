package com.gipit.bookshop_backend.components.Oauth2Client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gipit.bookshop_backend.components.JwtTokenUtil;
import com.gipit.bookshop_backend.dto.response.AuthenticationResponse;
import com.gipit.bookshop_backend.models.Token;
import com.gipit.bookshop_backend.models.User;
import com.gipit.bookshop_backend.services.impl.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DefaultOAuth2User oauthUser= (DefaultOAuth2User) authentication.getPrincipal();
        String email=oauthUser.getAttribute("email");
        User user= userService.findByEmail(email).orElseThrow(()->new RuntimeException());
        String token=jwtTokenUtil.generateToken(user.getUsername(), Token.TokenType.ACCESS);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        AuthenticationResponse auth=AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
        response.getWriter().write(new ObjectMapper().writeValueAsString(auth));
    }
}
