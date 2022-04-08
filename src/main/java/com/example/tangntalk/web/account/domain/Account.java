package com.example.tangntalk.web.account.domain;

import com.example.tangntalk.AES128;
import com.example.tangntalk.security.authorization.role.Role;
import lombok.*;
import org.hibernate.annotations.Check;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Check(constraints = "account_location IN ('공학관', '백양관', '학생회관', '신촌역')")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account implements UserDetails {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String name;

    private String email;

    @Column(nullable = false)
    private String password;

    private String statusMessage;

    @Column(nullable = false)
    private String accountType;

    @Column(nullable = false)
    private String accountLocation;

    @Column(nullable = false)
    private Boolean connectionStatus;

    @Enumerated(EnumType.STRING)
    private Role role;

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


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role.getAuthority());
    }

    @Override
    public String getUsername() {
        return username;
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
