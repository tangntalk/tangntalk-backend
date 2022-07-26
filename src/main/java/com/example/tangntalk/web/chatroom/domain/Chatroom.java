package com.example.tangntalk.web.chatroom.domain;

import com.example.tangntalk.web.message.domain.Message;
import com.example.tangntalk.web.account.domain.Account;
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
public class Chatroom {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long chatroomId;

    @OneToOne(fetch = FetchType.LAZY)
    private Account user1;

    @OneToOne(fetch = FetchType.LAZY)
    private Account user2;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true,
            cascade = CascadeType.ALL)
    private Message lastMessage;

    @Builder.Default
    @OneToMany(mappedBy = "chatroom",
                orphanRemoval = true,
                fetch = FetchType.LAZY,
                cascade = CascadeType.ALL)
    private Set<Message> messages = new HashSet<>();

    public void addMessage(Message message){
        message.setChatroom(this);
        this.messages.add(message);
        this.lastMessage = message;
    }

}
