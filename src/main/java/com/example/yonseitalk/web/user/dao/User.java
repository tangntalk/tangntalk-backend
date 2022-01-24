package com.example.yonseitalk.web.user.dao;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "yt_user")
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String name;

    @Column
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String role;

    @Column
    private String statusMessage;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String userLocation;

    @Column(nullable = false)
    private Boolean connectionStatus;

    @Builder.Default
    @JoinTable(name = "friends",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "friend_id")}
    )
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<User> userAddedFriends = new HashSet<>();

    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "userAddedFriends", fetch = FetchType.LAZY)
    private Set<User> friendsAddedUser = new HashSet<>();


}
