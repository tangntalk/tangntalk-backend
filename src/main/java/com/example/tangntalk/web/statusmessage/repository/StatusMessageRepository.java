package com.example.tangntalk.web.statusmessage.repository;

import com.example.tangntalk.web.statusmessage.domain.StatusMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusMessageRepository extends JpaRepository<StatusMessage, Long> {
    

}
