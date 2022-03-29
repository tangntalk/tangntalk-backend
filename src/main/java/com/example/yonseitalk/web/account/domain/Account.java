package com.example.yonseitalk.web.account.domain;

import com.example.yonseitalk.AES128;
import com.example.yonseitalk.security.authorization.role.AccountRole;
import lombok.*;
import org.hibernate.annotations.Check;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO: CHECK Constraint Test Case 추가 필요
 */

@Getter
@Setter
@Entity
@Table(name = "yt_account")
@Check(constraints = "account_location IN ('공학관', '백양관', '학생회관', '신촌역')")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account implements UserDetails {

    @Id
    @Column(nullable = false)
    private String accountId;

    @Column(nullable = false)
    private String name;

    @Column
    private String email;

    @Convert(converter = AES128.class)
    @Column(nullable = false)
    private String password;

    @Column
    private String role;



    @Column
    private String statusMessage;

    @Column(nullable = false)
    private String type;

//    @Column(nullable = false)
    @Column(nullable = false)
    private String accountLocation;

    @Column(nullable = false)
    private Boolean connectionStatus;

    @Builder.Default
    @JoinTable(name = "friends",
                joinColumns = {@JoinColumn(name = "account_id")},
                inverseJoinColumns = {@JoinColumn(name = "friend_id")}
    )
    @ManyToMany(cascade = CascadeType.ALL,
                fetch = FetchType.LAZY)
    private Set<Account> accountAddedFriends = new HashSet<>();

    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL,
                mappedBy = "accountAddedFriends",
                fetch = FetchType.LAZY)
    private Set<Account> friendsAddedAccount = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private AccountRole accountRole = AccountRole.NORMAL;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(accountRole.getAuthority());
    }

    @Override
    public String getUsername() {
        return accountId;
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

//    @Builder.Default
//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "users")
//    private Set<Chatroom> chatrooms = new HashSet<>();


}
