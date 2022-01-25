package com.example.yonseitalk.web.message.dao;

import lombok.CustomLog;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
public class Message {

    @Id
    @Column(nullable = false, unique = true)
    private Long messageId;

    @ManyToOne()
    @Column(nullable = false)
    private Long chatroomId;

    @Column(nullable = false)
    private String senderId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Timestamp sendTime;

    @Column(nullable = false)
    private Timestamp readTime;

    @Column(nullable = false)
    private Boolean rendezvousFlag;

    @Column(nullable = false)
    private String rendezvousLocation;

    @Column(nullable = false)
    private Timestamp rendezvousTime;


}
