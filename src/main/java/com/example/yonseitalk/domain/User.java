package com.example.yonseitalk.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "yt_user")
@ToString
public class User {

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

}
