package org.digit.exchange.repository;

import org.digit.exchange.model.messages.RequestMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestMessageRepository extends JpaRepository<RequestMessage, String> {
    // // Custom query methods
    Page<RequestMessage> findByHeaderSenderId(String senderId, Pageable pageable);
    Page<RequestMessage> findByHeaderReceiverId(String receiverId, Pageable pageable);
}
