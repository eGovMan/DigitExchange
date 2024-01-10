package org.digit.exchange.service;

import java.util.List;
import java.util.Optional;

import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.model.SearchRequest;
import org.digit.exchange.model.Message;
import org.digit.exchange.repository.MessageRepository;
import org.digit.exchange.security.CryptoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ExchangeService {

    private final MessageRepository messageRepository;
    private static final Logger logger = LoggerFactory.getLogger(ExchangeService.class);


    public ExchangeService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message processMessage(Message message) {        
        try{
            if(message.getSignature() !=null && message.getSignature().isEmpty())
                CryptoUtil.verify(message.getMessage(), message.getSignature(), null);
            return messageRepository.save(message);
        }catch(Exception e){
            logger.error("Error processing message", e);
            throw new CustomException("Error processing message.", e);
        }
    }

    // Find by ReceiverId
    public Page<Message> findByReceiverId(SearchRequest message) {
        try{
            String receiverId = message.getSearchString();
            int page = message.getPage();
            int size = message.getSize();
            Pageable pageable = PageRequest.of(page, size);
            
            Page<Message> result = messageRepository.findByHeaderReceiverId(receiverId,pageable);
            return result;
        } catch (Exception e) {
            throw new CustomException("Error processing message object.", e);
        }
    }

    public Page<Message> findBySenderId(SearchRequest message) {
        try{
            String senderId = message.getSearchString();
            int page = message.getPage();
            int size = message.getSize();
            Pageable pageable = PageRequest.of(page, size);
            
            Page<Message> result = messageRepository.findByHeaderSenderId(senderId,pageable);
            return result;
        } catch (Exception e) {
            throw new CustomException("Error processing message object.", e);
        }
    }

    // // Find by Sender Id
    // public Page<Message> findBySenderId(String id, Pageable pageable) {
    //     return messageRepository.findByHeaderSenderId(id,pageable);
    // }
}
