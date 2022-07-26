package com.example.tangntalk.web.message.domain;

import com.example.tangntalk.AES128;
import com.example.tangntalk.web.chatroom.domain.Chatroom;
import com.example.tangntalk.web.account.domain.Account;
import lombok.*;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
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

    @ManyToOne(fetch = FetchType.LAZY)
    private Account sender;

    @Convert(converter = AES128.class)
    @Column(nullable = false)
    private String content;

    @Column(columnDefinition = "TIMESTAMP")
    private Date sendTime;

    @Setter
    @Column(columnDefinition = "TIMESTAMP NULL")
    private Date readTime;

    @Setter
    @Column(nullable = false)
    private Boolean rendezvousFlag;

    @Setter
    @Column
    private String rendezvousLocation;

    @Setter
    @Column(columnDefinition = "TIMESTAMP")
    private Date rendezvousTime;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private Chatroom chatroom;
}
