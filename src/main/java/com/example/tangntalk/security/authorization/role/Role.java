package com.example.tangntalk.security.authorization.role;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


public enum Role {
    NORMAL(new SimpleGrantedAuthority("NORMAL")),
    ADMIN(new SimpleGrantedAuthority("NORMAL"));

    private final GrantedAuthority authority;

    Role(GrantedAuthority authority) {
        this.authority = authority;
    }

    public GrantedAuthority getAuthority() {
        return authority;
    }
}
