package org.digit.exchange.repository;

import org.digit.exchange.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {
    // // Custom query methods
    Page<Message> findByHeaderSenderId(String senderId, Pageable pageable);
    Page<Message> findByHeaderReceiverId(String receiverId, Pageable pageable);
}
