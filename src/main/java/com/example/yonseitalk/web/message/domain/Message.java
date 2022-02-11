package com.example.yonseitalk.web.message.domain;

import com.example.yonseitalk.web.chatroom.domain.Chatroom;
import com.example.yonseitalk.web.account.domain.Account;
import lombok.*;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Check(constraints = "rendezvous_location IN ('공학관', '백양관', '학생회관', '신촌역', null)")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long messageId;

//    @Column(nullable = false)
//    private Long chatroomId;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Account sender;

    @Column(nullable = false)
    private String content;

    @Column(nullable = true, columnDefinition = "TIMESTAMP")
    private Date sendTime;

    @Column(nullable = true, columnDefinition = "TIMESTAMP")
    private Date readTime;

    @Column(nullable = false)
    private Boolean rendezvousFlag;

    @Column
    private String rendezvousLocation;

    @Column(nullable = true, columnDefinition = "TIMESTAMP")
    private Date rendezvousTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private Chatroom chatroom;
}
