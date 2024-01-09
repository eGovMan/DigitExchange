package org.digit.exchange.controller;

import org.digit.exchange.dto.MessageDTO;
import org.digit.exchange.dto.NewMessageDTO;
import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.exceptions.ResourceNotFoundException;
import org.digit.exchange.model.SearchRequest;
import org.digit.exchange.model.Header;
import org.digit.exchange.model.Message;
import org.digit.exchange.service.ExchangeService;
import org.postgresql.translation.messages_sr;
import org.springframework.data.domain.Page;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("exchange/v1")
public class ExchangeController {

    private final ExchangeService service;
    // private static final Logger logger = LoggerFactory.getLogger(IndividualController.class);

    public ExchangeController(ExchangeService service){
        this.service = service;
    }

    //Routes Message from Sender to Receiver
    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody NewMessageDTO newMessageDto) {
        try{
            Message message = new Message();
            message.setMessage(newMessageDto.getMessage());
            message.getHeader().setReceiverId(newMessageDto.getTo());
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null) {
                message.getHeader().setSenderId(newMessageDto.getFrom());
            }else
                ResponseEntity.badRequest().build();
            return ResponseEntity.ok(new MessageDTO(service.processMessage(message))); 
        } catch (ResourceNotFoundException e) {
            throw new CustomException("Error saving user", e);
        }
    }   
    
    // //Routes Message from Sender to Receiver
    // @RequestMapping(value = "/send", method = RequestMethod.POST)
    // public ResponseEntity<Message> reply(@RequestBody Message message) {
    //     try{
    //         return ResponseEntity.ok(service.send(message)); 
    //     } catch (ResourceNotFoundException e) {
    //         throw new CustomException("Error saving user", e);
    //     }
    // }   

    // @Operation(summary = "Retrieves message messages for a user.", description = "Retrieves message messages for a user.")
    @RequestMapping(value = "/inbox", method = RequestMethod.POST)
    public ResponseEntity<Page<Message>> inbox(@RequestBody SearchRequest message) {
        try {
            Page<Message> result = service.findByReceiverId(message);
            return ResponseEntity.ok(result);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
    
    
    // // @Operation(summary = "Retrieves message messages sent by a user.", description = "Retrieves message messages sent by a user.")
    // @RequestMapping(value = "/sentitems", method = RequestMethod.POST)
    // public ResponseEntity<Page<Message>> sentItems(String senderId, Pageable pageable) {
    //     return ResponseEntity.ok(service.findBySenderId(senderId, pageable));
    // }

}
