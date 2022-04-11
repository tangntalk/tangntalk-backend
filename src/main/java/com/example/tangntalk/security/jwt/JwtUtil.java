package com.example.tangntalk.security.jwt;

import com.example.tangntalk.exception.UserNotFoundException;
import com.example.tangntalk.security.UserDetails.CustomUserDetailsService;
import com.example.tangntalk.security.authorization.role.Role;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@Slf4j
@Component
public class JwtUtil {

    private final long EXPIRE_TIME_MILLIS = 1000 * 60 * 30; // 30분

    private final String SIGNING_KEY;

    private String secretKey = "secretKey-test-authorization-jwt-manage-token";
    private String AUTHORITIES_KEY = "role";
    public final static String JWT_COOKIE_KEY = "tngntlk-auth";

    private final CustomUserDetailsService customUserDetailsService;

    public JwtUtil(CustomUserDetailsService customUserDetailsService, @Value("${jwt.raw-secret}") String rawSecret) {
        this.customUserDetailsService = customUserDetailsService;
        this.SIGNING_KEY = Base64.getEncoder().encodeToString(rawSecret.getBytes());
    }

    public String issueToken(String username, String role){
        Claims claims = Jwts.claims();
        claims.setSubject(username);
        claims.put(AUTHORITIES_KEY, role);

        Date now = new Date();
        Date expireDate = new Date(now.getTime() + EXPIRE_TIME_MILLIS);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    public boolean verifyToken(String token) {
        try {
            return extractAllClaims(token).getExpiration().after(new Date());
        } catch (Exception e) {
            log.error("Invalid token");
            return false;
        }
    }


    private Claims extractAllClaims(String token) {
        if (StringUtils.isEmpty(token)) return null;
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder().setSigningKey(SIGNING_KEY).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
            log.error(e.getMessage());
        }
        return claims;
    }


    public String extractToken(HttpServletRequest request) {
//        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        Cookie[] cookies = request.getCookies();

        if(cookies != null){
            for(Cookie cookie: cookies){
                if(cookie!=null && cookie.getName()!=null && cookie.getName().equals(JWT_COOKIE_KEY)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }



    public Authentication getAuthentication(String token) throws UserNotFoundException {
        Claims claims = extractAllClaims(token);
        String username = claims.getSubject();
        List<GrantedAuthority> authorities = Collections.singletonList(
                Role.valueOf(claims.get(AUTHORITIES_KEY).toString()).getAuthority()
        );

        UserDetails principal = new User(username, "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }
}
