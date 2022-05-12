package com.example.tangntalk.web.statusmessage.domain;

import com.example.tangntalk.web.account.domain.Account;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatusMessage {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long statusId;

    @Column(nullable = false)
    private String statusContent;

    @OneToOne(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private Account userId;

    @Column(columnDefinition = "TIMESTAMP NULL", nullable = false)
    private Date setTime;
}
