package org.digit.exchange.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.digit.exchange.models.*;

@Repository
public interface RequestMessageRepository extends JpaRepository<RequestMessage, String> {
    // // Custom query methods
    Page<RequestMessage> findByHeaderSenderId(String senderId, Pageable pageable);
    Page<RequestMessage> findByHeaderReceiverId(String receiverId, Pageable pageable);
}
