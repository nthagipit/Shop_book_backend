package com.gipit.bookshop_backend.components;

import com.gipit.bookshop_backend.exception.AppException;
import com.gipit.bookshop_backend.exception.ErrorCode;
import com.gipit.bookshop_backend.models.Role;
import com.gipit.bookshop_backend.models.Token;
import com.gipit.bookshop_backend.models.User;
import com.gipit.bookshop_backend.services.impl.TokenService;
import com.gipit.bookshop_backend.services.impl.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
@Getter
@Service
public class JwtTokenUtil {

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    @Autowired
    @Lazy
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    public String generateToken(String  username, Token.TokenType tokenType) {
        User user = userService.getUserByUsername(username);
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("userId",user.getUserID());
        claims.put("role",user.getListRoles().stream().map(Role::getRoleName).collect(Collectors.toList()));
        String token;
        try {
             token= Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(new Date(Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                    .setSubject(user.getFirstName() + " "+user.getLastName())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .signWith(getKey(), SignatureAlgorithm.HS256)
                    .compact();

        } catch (Exception e) {
            throw new InvalidParameterException("Failed to generate token: " + e.getMessage());
        }
        Token tokenEntity=Token.builder()
                .token(token)
                .tokenType(tokenType)
                .expirationDate(LocalDateTime.now().plusSeconds(VALID_DURATION))
                .revoked(false)
                .expired(false)
                .user(user)
                .build();
        tokenService.saveToken(tokenEntity);
        return token;
    }

    public SecretKey getKey(){
        return Keys.hmacShaKeyFor(SIGNER_KEY.getBytes(StandardCharsets.UTF_8));
    }
    public String extractUsername(String token) {
        return extractClaim(token,claim-> claim.get("username",String.class));
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username= extractUsername(token);
        Token tokenEntity=tokenService.getToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && tokenEntity!=null && !tokenEntity.isRevoked() && !tokenEntity.isExpired());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims= extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
            return Jwts.parser()
                    .setAllowedClockSkewSeconds(REFRESHABLE_DURATION)
                    .verifyWith(getKey())
                    .build().parseSignedClaims(token).getPayload();
    }
    //Kiểm tra JWT đã hết hạn hay chưa
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    //Kiểm tra thời gian hết hạn của JWT
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    //Check JWT có đang nằm trong thời gian refresh hay không
    public boolean isTokenRefreshable(String token) {
        Date expiration = extractExpiration(token);
        Instant now = Instant.now();
        Instant expirationInstant=expiration.toInstant();
        return now.isBefore(expirationInstant.plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)) && now.isAfter(expirationInstant)    ;
    }
}
