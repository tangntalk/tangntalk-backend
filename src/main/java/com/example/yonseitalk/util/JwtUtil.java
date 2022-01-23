package com.example.yonseitalk.util;

import com.example.yonseitalk.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private String secretKey = "secretKey-test-authorization-jwt-manage-token";
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String BEARER_PREFIX = "Bearer";

    private final UserDetailsService userDetailsService;


    private String createToken(Map<String, Object> claims) {

//        String secretKeyEncodeBase64 = Encoders.BASE64.encode(secretKey.getBytes());
//        byte[] keyBytes = Decoders.BASE64.decode(secretKeyEncodeBase64);
//        Key key = Keys.hmacShaKeyFor(keyBytes);


        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256,key)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .compact();

    }

    private Claims extractAllClaims(String token) {
        if (StringUtils.isEmpty(token)) return null;
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (JwtException e) {
            claims = null;
        }
        return claims;
    }


    public String extractToken(HttpServletRequest request) {
        String authorization = request.getHeader(AUTHORIZATION_HEADER);

        if (authorization == null) {
            return null;
        }
        if (!authorization.startsWith(BEARER_PREFIX)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 인증 헤더입니다.");
        }
        return authorization.substring(BEARER_PREFIX.length() + 1);
    }

    public boolean verifyToken(String token) {
        try {
            return extractAllClaims(token).getExpiration().after(new Date());
        } catch (Exception e) {
            log.error("Invalid token");
            return false;
        }
    }



    public String extractUsername(String token) {
        final Claims claims = extractAllClaims(token);
        if (claims == null) return null;
        else return (String) claims.get("user_id");
    }

    public String generateToken(User users) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", users.getUserId());
        return createToken(claims);
    }

    public Authentication getAuthentication(String token) {
        String email = extractAllClaims(token).get("user_id").toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
