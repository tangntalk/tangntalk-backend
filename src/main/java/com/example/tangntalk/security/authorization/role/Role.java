package com.example.tangntalk.security.authorization.role;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


public enum Role {
    NORMAL(new SimpleGrantedAuthority("NORMAL")),
    ADMIN(new SimpleGrantedAuthority("ADMIN"));

    private final GrantedAuthority authority;

    Role(GrantedAuthority authority) {
        this.authority = authority;
    }

    public GrantedAuthority getAuthority() {
        return authority;
    }

    public static Role of(String role){
        switch (role){
            case "일반": case "학생":  case "강사": case "기업":
                return Role.NORMAL;
            default:
                throw new IllegalArgumentException("존재하지 않는 사용자 종류입니다.");

        }
    }
}
