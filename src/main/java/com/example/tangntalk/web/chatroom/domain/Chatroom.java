package com.example.tangntalk.web.chatroom.domain;

import com.example.tangntalk.web.message.domain.Message;
import com.example.tangntalk.web.account.domain.Account;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Chatroom {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long chatroomId;

    @ManyToOne
    @JoinColumn(name = "user_1")
    private Account user1;

    @ManyToOne
    @JoinColumn(name = "user_2")
    private Account user2;

    @OneToOne
    @JoinColumn(name = "lastMessageId")
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
