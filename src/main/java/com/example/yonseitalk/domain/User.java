package com.example.yonseitalk.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "yt_user")
public class User implements UserDetails {

    @Id
    @Column(nullable=false, name = "user_id")
    private String user_id;

    @Column(nullable=false, name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(nullable=false, name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "status_message")
    private String status_message;

    @Column(nullable=false, name = "type")
    private String type;

    @Column(nullable=false, name = "user_location")
    private String user_location;

    @Column(nullable=false, name = "connection_status")
    private Boolean connection_status;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", user_id='" + user_id + '\'' +
                ", password='" + password + '\'' +
                ", status_message='" + status_message + '\'' +
                ", type='" + type + '\'' +
                ", user_location='" + user_location + '\'' +
                ", connection_status=" + connection_status +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        for(String role : role.split(",")){
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return user_id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
