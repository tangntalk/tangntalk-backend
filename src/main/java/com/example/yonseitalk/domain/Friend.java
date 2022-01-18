package com.example.yonseitalk.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;

@Getter
@Setter
@ToString
@Entity
public class Friend {
    private String user_id;
    private String friend_id;
}
