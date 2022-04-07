package com.example.tangntalk.security.authorization.role;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@Getter
public enum AccountRole {
    NORMAL(new SimpleGrantedAuthority("normal")),
    ADMIN(new SimpleGrantedAuthority("admin"));

    private final GrantedAuthority authority;

    AccountRole(GrantedAuthority authority) {
        this.authority = authority;
    }

}
