package com.example.yonseitalk.web.chatroom.domain;

import com.example.yonseitalk.web.message.domain.Message;
import com.example.yonseitalk.web.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Chatroom {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long chatroomId;

    @ManyToOne
    @JoinColumn(name = "user_1")
    private User user1;


    @ManyToOne
    @JoinColumn(name = "user_2")
    private User user2;

//    @Builder.Default
//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private Set<User> users = new HashSet<>();


    @OneToOne
    @JoinColumn(name = "last_message_id")
    private Message lastMessage;

    // TODO: @Getter, @Setter Access Level None 추가
    @Builder.Default
    @OneToMany(mappedBy = "chatroom",
                orphanRemoval = true,
                fetch = FetchType.LAZY,
                cascade = CascadeType.ALL)
    private Set<Message> messages = new HashSet<>();
}
