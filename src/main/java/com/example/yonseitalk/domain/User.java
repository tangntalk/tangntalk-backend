package com.example.yonseitalk.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


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

}
